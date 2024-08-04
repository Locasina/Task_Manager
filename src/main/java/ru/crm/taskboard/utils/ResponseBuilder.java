package ru.crm.taskboard.utils;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.crm.taskboard.data.dto.response.ResponseFail;
import ru.crm.taskboard.data.dto.response.ResponseSuccess;

import java.util.AbstractMap;
import java.util.List;

public final class ResponseBuilder {

    public static final String INTERNAL_SERVER_ERROR = "Внутренняя ошибка сервера!";
    public static final String LOGIN_EXCEPTION = "Ошибка во время аутентификации!";
    public static final String GET_ACCOUNT_EXCEPTION = "Ошибка во время получения информации об аккаунте пользователя!";
    public static final String GET_ACCOUNT_LIST_EXCEPTION = "Ошибка во время получения списка информации об аккаунтах пользователей!";
    public static final String CREATE_ACCOUNT_EXCEPTION = "Ошибка во время создания аккаунта пользователя!";
    public static final String UPDATE_ACCOUNT_EXCEPTION = "Ошибка во время изменения информации об аккаунте пользователя!";
    public static final String DELETE_ACCOUNT_EXCEPTION = "Ошибка во время удаления информации об аккаунте пользователя!";

    public static ResponseEntity<?> createResponseSuccessWithMessage(String message, HttpStatus httpStatus) {
        return new ResponseEntity<>(new ResponseSuccess("message", message), httpStatus);
    }

    public static ResponseEntity<?> createResponseSuccess(String resourceName, Object result, HttpStatus httpStatus) {
        return new ResponseEntity<>(new ResponseSuccess(resourceName, result), httpStatus);
    }

    public static ResponseEntity<?> createResponseFail(String error, HttpStatus httpStatus) {
        return new ResponseEntity<>(new ResponseFail(error), httpStatus);
    }

    public static ResponseEntity<Object> createResponseFails(List<String> errors, HttpStatus httpStatus) {
        return new ResponseEntity<>(new ResponseFail(String.join(", ", errors)), httpStatus);
    }

    public static ResponseEntity<?> createResponseFile(AbstractMap.SimpleEntry<HttpHeaders, FileSystemResource> fileData,
                                                       HttpStatus httpStatus) {
        return new ResponseEntity<>(fileData.getValue(), fileData.getKey(), httpStatus);
    }
}
