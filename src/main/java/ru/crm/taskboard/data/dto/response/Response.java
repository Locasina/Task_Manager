package ru.crm.taskboard.data.dto.response;

public abstract class Response {

    private final boolean success;

    public Response(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
