package ru.crm.taskboard.web.controllers;

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
import org.springframework.web.bind.annotation.*;
import ru.crm.taskboard.data.dto.AccountData;
import ru.crm.taskboard.utils.ResponseBuilder;
import ru.crm.taskboard.web.dto.CreateAccountRequest;
import ru.crm.taskboard.web.dto.UpdateAccountRequest;
import ru.crm.taskboard.web.exceptions.ResourceAlreadyInUseException;
import ru.crm.taskboard.web.exceptions.UserNotFoundException;
import ru.crm.taskboard.web.service.AccountService;

@Slf4j
@RestController
@RequestMapping("/api/account")
@Tag(name = "REST API для работы с аккаунтами пользователей.", description = "Предоставляет методы для управления аккаунтами пользователей.")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountId}")
    @Operation(summary = "Метод для получения аккаунта пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AccountData.class)), description = "Успешное получение данных аккаунта пользователя."),
            @ApiResponse(responseCode = "400", description = ResponseBuilder.GET_ACCOUNT_EXCEPTION),
            @ApiResponse(responseCode = "500", description = ResponseBuilder.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<?> getAccount(@Parameter(name = "Идентификатор аккаунта пользователя")
                                        @NotNull(message = "Идентификатор аккаунта пользователя не может быть пустым!")
                                        @PathVariable("accountId") Integer accountId) throws UserNotFoundException {
        return ResponseBuilder.createResponseSuccess("accountData", accountService.getAccountData(accountId), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Метод для получения списка аккаунтов пользователей")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AccountData.class)), description = "Успешное получение списка данных аккаунтов пользователей."),
            @ApiResponse(responseCode = "400", description = ResponseBuilder.GET_ACCOUNT_LIST_EXCEPTION),
            @ApiResponse(responseCode = "500", description = ResponseBuilder.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<?> getAccountList(@ParameterObject
                                            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseBuilder.createResponseSuccess(
                "accountDataList",
                accountService
                        .getAccountDataList(pageable), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Метод для создания аккаунта пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное создание аккаунта пользователя."),
            @ApiResponse(responseCode = "400", description = ResponseBuilder.CREATE_ACCOUNT_EXCEPTION),
            @ApiResponse(responseCode = "500", description = ResponseBuilder.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<?> createAccount(@Parameter(name = "Запрос создания аккаунта пользователя")
                                           @Valid @RequestBody CreateAccountRequest createAccountRequest,
                                           @ParameterObject
                                           @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) throws ResourceAlreadyInUseException {

        return ResponseBuilder.createResponseSuccess("accountDataList", accountService.createAccount(createAccountRequest, pageable), HttpStatus.OK);
    }

    @PutMapping
    @Operation(summary = "Метод для изменения данных аккаунта пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное изменение данных аккаунта пользователя."),
            @ApiResponse(responseCode = "400", description = ResponseBuilder.UPDATE_ACCOUNT_EXCEPTION),
            @ApiResponse(responseCode = "500", description = ResponseBuilder.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<?> updateAccount(@Parameter(name = "Запрос изменения данных аккаунта пользователя")
                                           @Valid @RequestBody UpdateAccountRequest updateAccountRequest,
                                           @ParameterObject
                                           @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) throws UserNotFoundException, ResourceAlreadyInUseException {

        return ResponseBuilder.createResponseSuccess("accountDataList", accountService.updateAccount(updateAccountRequest, pageable), HttpStatus.OK);
    }

    @DeleteMapping
    @Operation(summary = "Метод для удаления аккаунта пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное удаление аккаунта пользователя."),
            @ApiResponse(responseCode = "400", description = ResponseBuilder.DELETE_ACCOUNT_EXCEPTION),
            @ApiResponse(responseCode = "500", description = ResponseBuilder.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<?> deleteAccount(@Parameter(name = "Идентификатор аккаунта пользователя")
                                           @NotNull(message = "Идентификатор аккаунта пользователя не может быть пустым!")
                                           @RequestParam("accountId") Integer accountId,
                                           @ParameterObject
                                           @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseBuilder.createResponseSuccess("accountDataList", accountService.deleteAccount(accountId, pageable), HttpStatus.OK);
    }

}
