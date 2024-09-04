package ru.crm.taskboard.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Запрос создания аккаунта пользователя", description = "Запрос содержащий данные аккаунта пользователя для создания")
public class CreateAccountRequest {

    @NotEmpty(message = "Имя пользователя не может быть пустым!")
    @Schema(name = "personName", description = "Имя пользователя", allowableValues = "Непустая строка", required = true)
    private String personName;
    @NotEmpty(message = "Фамилия пользователя не может быть пустой!")
    @Schema(name = "personSurname", description = "Фамилия пользователя", allowableValues = "Непустая строка", required = true)
    private String personSurname;
    @NotEmpty(message = "Отчество пользователя не может быть пустым!")
    @Schema(name = "personPatronymic", description = "Отчество пользователя", allowableValues = "Непустая строка", required = true)
    private String personPatronymic;
    @NotEmpty(message = "Логин аккаунта не может быть пустым!")
    @Schema(name = "login", description = "Логин аккаунта пользователя", allowableValues = "Непустая строка", required = true)
    private String login;
    @NotEmpty(message = "Пароль аккаунта не может быть пустым!")
    @Schema(name = "password", description = "Пароль аккаунта пользователя", allowableValues = "Непустая строка", required = true)
    private String password;
    @NotEmpty(message = "Адрес эл. почты пользователя не может быть пустым!")
    @Schema(name = "email", description = "Адрес эл. почты пользователя", allowableValues = "Непустая строка", required = true)
    private String email;
    @NotNull(message = "Признак 'Активный/активированный аккаунт' не может быть пустым!")
    @Schema(name = "enabled", description = "Признак 'Активный/активированный аккаунт'", allowableValues = "TRUE/FALSE")
    private Boolean enabled;
}
