package ru.crm.taskboard.services.taskboard.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Запрос для создания рабочего пространства ", description = "Набор данных рабочего пространства")
public class CreateTaskBoardRequest {

    @NotNull(message = "Название рабочего пространства не может быть пустым!")
    @Schema(name = "title", description = "Название рабочего пространства", allowableValues = "непустая строка", required = true)
    private String title;
    @Schema(name = "description", description = "Описание рабочего пространства", allowableValues = "строка", required = false)
    private String description;
}
