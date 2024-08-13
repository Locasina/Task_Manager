package ru.crm.taskboard.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.crm.taskboard.data.entities.TaskBoard;

import java.util.Optional;

public interface TaskBoardRepository extends JpaRepository<TaskBoard, Integer> {
    @Override
    Optional<TaskBoard> findById(Integer integer);
}
