package ru.crm.taskboard.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.crm.taskboard.data.entities.TaskBoard;

import java.util.List;

public interface TaskBoardRepository extends JpaRepository<TaskBoard, Integer> {

    boolean existsByTitle(String title);

    boolean existsByTitleAndIdNot(String title, Integer id);

    @Query(value = "select tb from  TaskBoard tb " +
            "left join tb.accountsSet a " +
            " where a.id = :accountId")
    List<TaskBoard> getAllByFilter(@Param("accountId") Integer accountId);
}
