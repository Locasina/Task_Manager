package ru.crm.taskboard.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Данные колонок рабочего пространства", description = "Набор данных колонок рабочего пространства")
public class PillarData {

    @Schema(name = "pillarId", description = "Идентификатор колонки рабочего пространства", allowableValues = "Целое число, не равное 0", required = true)
    private Integer pillarId;
    @Schema(name = "taskBoardId", description = "Идентификатор рабочего пространства", allowableValues = "Целое число, не равное 0", required = true)
    private Integer taskBoardId;
    @Schema(name = "positionId", description = "Номер позиции в колонке", allowableValues = "Целое число, не равное 0", required = true)
    private Integer positionId;
    @Schema(name = "title", description = "Название колонки", allowableValues = "Непустая строка", required = true)
    private String title;
    @Schema(name = "isArchived", description = "Статус нахождения в архиве", allowableValues = "Булево значение", required = true)
    private Boolean isArchived;
    @Schema(name = "archivationDate", description = "Дата добавления в архив", allowableValues = "Дата в формате: ГГГГ:ММ:ДД", required = false)
    private LocalDate archivationDate;
    @Schema(name = "creationDate", description = "Дата создания", allowableValues = "Дата в формате: ГГГГ:ММ:ДД", required = true)
    private LocalDate creationDate;


}
