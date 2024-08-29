package ru.crm.taskboard.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.crm.taskboard.data.entities.Pillar;

import java.util.List;


public interface PillarRepository extends JpaRepository<Pillar, Integer> {
    List<Pillar> findByTaskBoard_Id(Integer id);
}
