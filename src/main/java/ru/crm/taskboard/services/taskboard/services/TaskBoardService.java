package ru.crm.taskboard.services.taskboard.services;

import org.springframework.stereotype.Service;
import ru.crm.taskboard.data.dto.TaskBoardData;
import ru.crm.taskboard.data.entities.TaskBoard;
import ru.crm.taskboard.data.repositories.TaskBoardRepository;
import ru.crm.taskboard.data.services.CommonConverterService;
import ru.crm.taskboard.services.taskboard.dto.CreateTaskBoardRequest;
import ru.crm.taskboard.services.taskboard.dto.UpdateTaskBoardRequest;
import ru.crm.taskboard.services.taskboard.exception.TaskBoardAlreadyExistsException;
import ru.crm.taskboard.services.taskboard.exception.TaskBoardNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskBoardService {
    private final TaskBoardRepository taskBoardRepository;
    private final TaskBoardConverterService taskBoardConverterService;
    private final CommonConverterService commonConverterService;

    public TaskBoardService(TaskBoardRepository taskBoardRepository,
                            TaskBoardConverterService taskBoardConverterService,
                            CommonConverterService commonConverterService) {
        this.taskBoardRepository = taskBoardRepository;
        this.taskBoardConverterService = taskBoardConverterService;
        this.commonConverterService = commonConverterService;
    }

    public TaskBoardData getTaskBoard(Integer id) throws TaskBoardNotFoundException {
        return commonConverterService.formTaskBoardData(getTaskBoardById(id));
    }

    public List<TaskBoardData> getAllTaskBoard() {
        List<TaskBoard> taskBoardList = taskBoardRepository.findAll();
        List<TaskBoardData> taskBoardDataList = new ArrayList<>();

        for (TaskBoard item : taskBoardList) {
            taskBoardDataList.add(commonConverterService.formTaskBoardData(item));
        }

        return taskBoardDataList;
    }

    public TaskBoard createTaskBoard(CreateTaskBoardRequest request) throws TaskBoardAlreadyExistsException {
        if (taskBoardRepository.existsByTitle(request.getTitle())) {
            throw new TaskBoardAlreadyExistsException("Рабочее пространство с таким именем существует!");
        }

        return taskBoardRepository.save(taskBoardConverterService.formTaskBoard(request));
    }

    public TaskBoard updateTaskBoard(UpdateTaskBoardRequest request) throws TaskBoardNotFoundException, TaskBoardAlreadyExistsException {
        if (taskBoardRepository.existsByTitleAndIdNot(request.getTitle(), request.getId())) {
            throw new TaskBoardAlreadyExistsException("Рабочее пространство с таким именем существует!");
        }

        TaskBoard taskBoard = getTaskBoardById(request.getId());

        taskBoard.setTitle(request.getTitle());
        taskBoard.setDescription(request.getDescription());
        taskBoard.setIsArchived(request.getIsArchived());

        return taskBoardRepository.save(taskBoard);
    }

    public Object deleteTaskBoard(Integer id) {
        if (taskBoardRepository.existsById(id)) {
            taskBoardRepository.deleteById(id);
        }

        return getAllTaskBoard();
    }

    private TaskBoard getTaskBoardById(Integer id) throws TaskBoardNotFoundException {
        return taskBoardRepository
                .findById(id)
                .orElseThrow(() -> new TaskBoardNotFoundException(String.format("Рабочее пространство с идентификатором: %s не найдено!", id)));
    }

}
