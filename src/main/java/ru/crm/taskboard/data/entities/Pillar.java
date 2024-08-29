package ru.crm.taskboard.data.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "pillar")
public class Pillar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "task_board_id")
    private TaskBoard taskBoard;
    @Column(name = "position_number")
    private Integer positionNumber;
    @Column(name = "title")
    private String title;
    @Column(name = "is_archived")
    private boolean isArchived;
    @Column(name = "archivation_date")
    private LocalDate archivationDate;
    @Column(name = "creation_date")
    private LocalDate creationDate;

}
