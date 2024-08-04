package ru.crm.taskboard.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.crm.taskboard.data.entities.TokenBlacklist;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Integer> {

    Optional<TokenBlacklist> findByToken(String token);

    void deleteAllByExpirationTimeBefore(LocalDateTime localDateTime);
}
