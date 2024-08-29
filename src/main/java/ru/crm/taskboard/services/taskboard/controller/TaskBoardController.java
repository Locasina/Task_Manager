package ru.crm.taskboard.services.taskboard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.crm.taskboard.data.dto.TaskBoardData;
import ru.crm.taskboard.services.taskboard.dto.CreateTaskBoardRequest;
import ru.crm.taskboard.services.taskboard.dto.UpdateTaskBoardRequest;
import ru.crm.taskboard.services.taskboard.exception.TaskBoardAlreadyExistsException;
import ru.crm.taskboard.services.taskboard.exception.TaskBoardNotFoundException;
import ru.crm.taskboard.services.taskboard.services.TaskBoardService;
import ru.crm.taskboard.utils.ResponseBuilder;
import ru.crm.taskboard.web.exceptions.ResourceAlreadyInUseException;

@Slf4j
@RestController
@RequestMapping("/api/task-board")
@Tag(name = "REST API для работы с рабочим пространством.", description = "Предоставляет методы для управления рабочими пространствами.")
public class TaskBoardController {

    private final TaskBoardService taskBoardService;

    public TaskBoardController(TaskBoardService taskBoardService) {
        this.taskBoardService = taskBoardService;
    }

    @GetMapping("/{taskBoardId}")
    @Operation(summary = "Метод для получения рабочего пространства")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = TaskBoardData.class)), description = "Успешное получение данных рабочего пространства."),
            @ApiResponse(responseCode = "400", description = ResponseBuilder.GET_TASK_BOARD_EXCEPTION),
            @ApiResponse(responseCode = "500", description = ResponseBuilder.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<?> getTaskBoard(@Parameter(name = "Идентификатор рабочего пространства")
                                          @NotNull(message = "Идентификатор рабочего пространства не может быть пустым!")
                                          @PathVariable("taskBoardId") Integer taskBoardId) throws TaskBoardNotFoundException {
        return ResponseBuilder.createResponseSuccess("accountData", taskBoardService.getTaskBoard(taskBoardId), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Метод для получения списка рабочих пространств")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = TaskBoardData.class)), description = "Успешное получение списка данных рабочих пространств."),
            @ApiResponse(responseCode = "400", description = ResponseBuilder.GET_TASK_BOARD_LIST_EXCEPTION),
            @ApiResponse(responseCode = "500", description = ResponseBuilder.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<?> getTaskBoardList(@Parameter(name = "Имя пользователя")
                                              @NotNull(message = "Идентификатор пользователя не может быть пустым!")
                                              @RequestParam("accountId") Integer accountId) {
        return ResponseBuilder.createResponseSuccess(
                "taskBoardDataList",
                taskBoardService
                        .getAllTaskBoard(accountId), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Метод для создания аккаунта пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное создание аккаунта пользователя."),
            @ApiResponse(responseCode = "400", description = ResponseBuilder.CREATE_TASK_BOARD_EXCEPTION),
            @ApiResponse(responseCode = "500", description = ResponseBuilder.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<?> createTaskBoard(@Parameter(name = "Запрос создания рабочего пространства")
                                             @Valid @RequestBody CreateTaskBoardRequest createTaskBoardRequest,
                                             @Parameter(name = "Идентификатор рабочего пространства")
                                             @Nullable @RequestParam("TaskBoardId") String taskBoardId,
                                             @Parameter(name = "Название рабочего пространства")
                                             @Nullable @RequestParam("title") String title,
                                             @Parameter(name = "Описание рабочего пространства")
                                             @Nullable @RequestParam("description") String description,
                                             @Parameter(name = "Статус нахождения в архиве")
                                             @Nullable @RequestParam("isArchived") String isArchived,
                                             @Parameter(name = "Дата создания рабочего пространства")
                                             @Nullable @RequestParam("creationDate") String creationDate,
                                             @Parameter(name = "Дата архивации рабочего пространства")
                                             @Nullable @RequestParam("archiveDate") String archiveDate,
                                             @Parameter(name = "Список данных рабочего пространства")
                                             @Nullable @RequestParam("taskBoardDataList") String taskBoardDataList
    ) throws ResourceAlreadyInUseException, TaskBoardAlreadyExistsException {

        return ResponseBuilder.createResponseSuccess("accountDataList", taskBoardService.createTaskBoard(createTaskBoardRequest), HttpStatus.OK);
    }

    @PutMapping
    @Operation(summary = "Метод для изменения данных рабочего пространства")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное изменение данных рабочего пространства."),
            @ApiResponse(responseCode = "400", description = ResponseBuilder.UPDATE_TASK_BOARD_EXCEPTION),
            @ApiResponse(responseCode = "500", description = ResponseBuilder.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<?> updateTaskBoard(@Parameter(name = "Запрос изменения данных рабочего пространства")
                                             @Valid @RequestBody UpdateTaskBoardRequest updateTaskBoardRequest
    ) throws TaskBoardNotFoundException, ResourceAlreadyInUseException, TaskBoardAlreadyExistsException {

        return ResponseBuilder.createResponseSuccess("accountDataList", taskBoardService.updateTaskBoard(updateTaskBoardRequest), HttpStatus.OK);
    }

    @DeleteMapping
    @Operation(summary = "Метод для удаления рабочего пространства")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное удаление рабочего пространства."),
            @ApiResponse(responseCode = "400", description = ResponseBuilder.DELETE_TASK_BOARD_EXCEPTION),
            @ApiResponse(responseCode = "500", description = ResponseBuilder.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<?> deleteTaskBoard(@Parameter(name = "Идентификатор рабочего пространства")
                                             @NotNull(message = "Идентификатор рабочего пространства не может быть пустым!")
                                             @RequestParam("taskBoardId") Integer taskBoardId,
                                             @ParameterObject
                                             @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseBuilder.createResponseSuccess("accountDataList", taskBoardService.deleteTaskBoard(taskBoardId), HttpStatus.OK);
    }
}
