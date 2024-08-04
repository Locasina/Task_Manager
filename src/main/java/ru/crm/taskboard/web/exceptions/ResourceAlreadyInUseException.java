package ru.crm.taskboard.web.exceptions;

public class ResourceAlreadyInUseException extends Exception {

    public ResourceAlreadyInUseException(String error) {
        super(error);
    }
}
