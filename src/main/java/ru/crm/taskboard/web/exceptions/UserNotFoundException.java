package ru.crm.taskboard.web.exceptions;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(String error) {
        super(error);
    }
}
