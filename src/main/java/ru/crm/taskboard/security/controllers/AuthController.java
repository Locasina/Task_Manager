package ru.crm.taskboard.security.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.crm.taskboard.security.dto.LoginRequest;
import ru.crm.taskboard.security.dto.LogoutRequest;
import ru.crm.taskboard.security.exceptions.InvalidPasswordException;
import ru.crm.taskboard.security.services.AuthService;
import ru.crm.taskboard.utils.ResponseBuilder;

import javax.security.auth.login.LoginException;


@Slf4j
@RestController
@RequestMapping("/api/auth")
@Tag(name = "REST API для авторизации и аутентификации", description = "Предоставляет методы для авторизации и аутентификации. По умолчанию не защищён.")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Метод для аутентификации пользователя в системе.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный вход в систему."),
            @ApiResponse(responseCode = "400", description = ResponseBuilder.LOGIN_EXCEPTION),
            @ApiResponse(responseCode = "500", description = ResponseBuilder.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<?> login(@Parameter(name = "Логин/пароль для аутентификации")
                                   @Valid @RequestBody LoginRequest loginRequest) throws UsernameNotFoundException, InvalidPasswordException {
        return ResponseBuilder.createResponseSuccess("token", authService.login(loginRequest), HttpStatus.OK);
    }

    @GetMapping("/refresh")
    @Operation(summary = "Метод для обновления токена и аутентификации пользователя в системе.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный вход в систему."),
            @ApiResponse(responseCode = "400", description = ResponseBuilder.LOGIN_EXCEPTION),
            @ApiResponse(responseCode = "500", description = ResponseBuilder.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<?> refreshToken(@Parameter(name = "Имя пользователя")
                                          @Nullable @RequestParam("token") String token) throws UsernameNotFoundException, LoginException {
        return ResponseBuilder.createResponseSuccess("token", authService.refreshToken(token), HttpStatus.OK);
    }

    @PostMapping("/logout")
    @Operation(summary = "Метод для выхода из системы.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный выход из системы."),
            @ApiResponse(responseCode = "500", description = ResponseBuilder.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity<?> logout(@Parameter(name = "Данные для выхода из системы")
                                    @Valid @RequestBody LogoutRequest logoutRequest) {
        authService.logout(logoutRequest);

        return ResponseBuilder.createResponseSuccessWithMessage("Выход из системы был успешно произведён!", HttpStatus.OK);
    }
}
