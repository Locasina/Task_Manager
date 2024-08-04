package ru.crm.taskboard.security.exceptions;

public class InvalidPasswordException extends Exception {

    public InvalidPasswordException(String error) {
        super(error);
    }
}
