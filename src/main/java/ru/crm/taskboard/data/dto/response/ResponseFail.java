package ru.crm.taskboard.data.dto.response;

public class ResponseFail extends Response {

    private final String error;

    public ResponseFail(String error) {
        super(false);

        this.error = error;
    }

    public String getError() {
        return error;
    }
}
