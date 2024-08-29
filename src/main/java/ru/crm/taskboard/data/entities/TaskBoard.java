package ru.crm.taskboard.data.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

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
    @ManyToMany
    @JoinTable(
            name = "account_to_task_board",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "task_board_id")
    )
    private Set<Account> accountsSet;


}
