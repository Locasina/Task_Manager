package ru.crm.taskboard.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Запрос аутентификации", description = "Запрос содержащий данные для аутентификации пользователя")
public class LoginRequest {

    @NotEmpty(message = "Логин не может быть пустым!")
    @Schema(name = "login",
            allowableValues = "Непустая строка",
            description = "Логин/эл. почта",
            required = true)
    private String login;
    @NotEmpty(message = "Пароль не может быть пустым!")
    @Schema(name = "password", allowableValues = "Непустая строка", required = true)
    private String password;
}
