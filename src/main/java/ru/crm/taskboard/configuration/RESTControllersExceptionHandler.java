package ru.crm.taskboard.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.crm.taskboard.security.exceptions.InvalidPasswordException;
import ru.crm.taskboard.utils.ResponseBuilder;

import javax.security.auth.login.AccountNotFoundException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;

@Slf4j
@ControllerAdvice
public class RESTControllersExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleCommonException(Exception ex) {
        log.error(ex.getMessage(), ex);

        return ResponseBuilder.createResponseFail(ResponseBuilder.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<?> handleSecurityException(Exception ex) {
        log.debug(ex.getMessage(), ex);

        return ResponseBuilder.createResponseFail(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({UsernameNotFoundException.class, InvalidPasswordException.class,
            FileAlreadyExistsException.class, NotDirectoryException.class,
            FileNotFoundException.class, NoSuchFileException.class,
            IOException.class, AccountNotFoundException.class
    })

    public ResponseEntity<?> handleCustomException(Exception ex) {
        log.error(ex.getMessage(), ex);

        return ResponseBuilder.createResponseFail(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
