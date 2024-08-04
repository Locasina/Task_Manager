package ru.crm.taskboard.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Запрос обновления аккаунта пользователя", description = "Запрос содержащий данные для изменения аккаунта пользователя")
public class UpdateAccountRequest {

    @NotNull(message = "Идентификатор аккаунта пользователя не может быть пустым!")
    @Schema(name = "accountId", description = "Идентификатор аккаунта пользователя", allowableValues = "Непустое число", required = true)
    private Integer accountId;
    @Schema(name = "personName", description = "Имя пользователя", allowableValues = "Непустая строка")
    private String personName;
    @Schema(name = "personSurname", description = "Фамилия пользователя", allowableValues = "Непустая строка")
    private String personSurname;
    @Schema(name = "personPatronymic", description = "Отчество пользователя", allowableValues = "Непустая строка")
    private String personPatronymic;
    @Schema(name = "login", description = "Логин аккаунта пользователя", allowableValues = "Непустая строка")
    private String login;
    @Schema(name = "password", description = "Пароль аккаунта пользователя", allowableValues = "Непустая строка")
    private String password;
    @Schema(name = "email", description = "Адрес эл. почты пользователя", allowableValues = "Непустая строка")
    private String email;
    @Schema(name = "enabled", description = "Признак 'Активный/активированный аккаунт'", allowableValues = "TRUE/FALSE")
    private Boolean enabled;
    @Schema(name = "organizationPositionId", description = "Идентификатор должности компании", allowableValues = "Непустое число")
    private Integer organizationPositionId;
    @Schema(name = "organizationServiceNumber", description = "Табельный номер пользователя в компании", allowableValues = "Непустая строка")
    private String organizationServiceNumber;
}
