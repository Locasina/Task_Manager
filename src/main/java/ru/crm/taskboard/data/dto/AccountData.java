package ru.crm.taskboard.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Данные аккаунта пользователя", description = "Набор данных аккаунта пользователя")
public class AccountData {

    @Schema(name = "accountId", description = "Идентификатор аккаунта пользователя", allowableValues = "Целое число, не равное 0", required = true)
    private Integer accountId;
    @Schema(name = "organizationServiceNumber", description = "Табельный номер пользователя в компании", allowableValues = "Непустая строка", required = true)
    private String organizationServiceNumber;
    @Schema(name = "personName", description = "Имя пользователя", allowableValues = "Непустая строка", required = true)
    private String personName;
    @Schema(name = "personSurname", description = "Фамилия пользователя", allowableValues = "Непустая строка", required = true)
    private String personSurname;
    @Schema(name = "personPatronymic", description = "Отчество пользователя", allowableValues = "Непустая строка", required = true)
    private String personPatronymic;
    @Schema(name = "personFullName", description = "ФИО пользователя", allowableValues = "Непустая строка", required = true)
    private String personFullName;
    @Schema(name = "login", description = "Логин аккаунта пользователя", allowableValues = "Непустая строка", required = true)
    private String login;
    @Schema(name = "email", description = "Адрес эл. почты пользователя", allowableValues = "Непустая строка", required = true)
    private String email;
    @Schema(name = "language", description = "Язык интерфейса пользователя. Два языка: EN/RU, по-умолчанию RU", allowableValues = "EN/RU", required = true)
    private String language;
    @Schema(name = "enabled", description = "Признак 'Активный/активированный аккаунт'", allowableValues = "TRUE/FALSE", required = true)
    private Boolean enabled;
    @Schema(name = "admin", description = "Признак 'Аккаунт администратора'", allowableValues = "TRUE/FALSE", required = true)
    private Boolean admin;
    @Schema(name = "lastTimeActive", description = "Дата и время последней активности аккаунта", allowableValues = "Дата и время", required = true)
    private LocalDateTime lastTimeActive;
    @Schema(name = "creationTime", description = "Дата и время создания аккаунта", allowableValues = "Дата и время", required = true)
    private LocalDateTime creationTime;
    @Schema(name = "updateTime", description = "Дата и время обновления данных аккаунта", allowableValues = "Дата и время", required = true)
    private LocalDateTime updateTime;
}
