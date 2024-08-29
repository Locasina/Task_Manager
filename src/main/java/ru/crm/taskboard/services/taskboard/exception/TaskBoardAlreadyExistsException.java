package ru.crm.taskboard.services.taskboard.exception;

public class TaskBoardAlreadyExistsException extends Exception {
    public TaskBoardAlreadyExistsException(String message) {
        super(message);
    }
}
