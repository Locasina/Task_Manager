package ru.crm.taskboard.data.dto.response;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

import java.util.HashMap;
import java.util.Objects;

public class ResponseSuccess extends Response {

    private final HashMap<String, Object> map;

    public ResponseSuccess(String resourceName, Object result) {
        super(true);

        this.map = new HashMap<>();

        if (Objects.nonNull(resourceName)) {
            map.put(resourceName, result);
        }
    }

    @JsonAnyGetter
    public HashMap<String, Object> getMap() {
        return map;
    }
}
