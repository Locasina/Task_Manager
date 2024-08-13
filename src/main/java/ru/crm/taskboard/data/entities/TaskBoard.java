package ru.crm.taskboard.data.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "task_board")
public class TaskBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "is_archived")
    private Boolean isArchived;
    @Column(name = "create_date")
    private LocalDate createDate;
    @Column(name = "archive_date")
    private LocalDate archiveDate;
}
