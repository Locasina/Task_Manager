package ru.crm.taskboard.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Запрос выхода из системы", description = "Запрос содержащий данные для выхода из системы")
public class LogoutRequest {

    @NotEmpty(message = "Токен не может быть пустым!")
    @Schema(name = "token", description = "Токен доступа пользователя", allowableValues = "Непустая строка", required = true)
    private String token;
}
