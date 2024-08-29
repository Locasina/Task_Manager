package ru.crm.taskboard.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Данные рабочего пространства", description = "Набор данных рабочего пространства")
public class TaskBoardData {

    @Schema(name = "taskBoardId", description = "Идентификатор рабочего пространства", allowableValues = "Целое число, не равное 0", required = true)
    private Integer taskBoardId;
    @Schema(name = "title", description = "Название рабочего пространства", allowableValues = "Непустая строка", required = true)
    private String title;
    @Schema(name = "description", description = "Описание рабочего пространства", allowableValues = "Целое число, не равное 0", required = true)
    private String description;
    @Schema(name = "isArchived", description = "Статус нахождения в архиве", allowableValues = "Булево значение", required = true)
    private Boolean isArchived;
    @Schema(name = "createDate", description = "Дата создания рабочего пространства", allowableValues = "Дата в формате: ГГГГ:ММ:ДД", required = true)
    private LocalDate createDate;
    @Schema(name = "archiveDate", description = "Дата архивации рабочего пространства", allowableValues = "Дата в формате: ГГГГ:ММ:ДД", required = true)
    private LocalDate archiveDate;
    @Schema(name = "accountsDataList", description = "Список данных аккаунтов пользователей", allowableValues = "Лист объектов AccountData", required = true)
    private List<AccountData> accountsDataList;
}
