package ru.crm.taskboard.services.taskboard.services;

import org.springframework.stereotype.Service;
import ru.crm.taskboard.data.entities.TaskBoard;
import ru.crm.taskboard.services.taskboard.dto.CreateTaskBoardRequest;

import java.time.LocalDate;

@Service
public class TaskBoardConverterService {

    public TaskBoard formTaskBoard(CreateTaskBoardRequest request) {
        TaskBoard taskBoard = new TaskBoard();

        taskBoard.setTitle(request.getTitle());
        taskBoard.setDescription(request.getDescription());
        taskBoard.setCreateDate(LocalDate.now());

        return taskBoard;
    }
}
