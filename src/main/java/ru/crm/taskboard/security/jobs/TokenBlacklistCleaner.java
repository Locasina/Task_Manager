package ru.crm.taskboard.security.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.crm.taskboard.data.repositories.TokenBlacklistRepository;

import java.time.LocalDateTime;

@Slf4j
@Component
public class TokenBlacklistCleaner {

    private final TokenBlacklistRepository tokenBlacklistRepository;

    public TokenBlacklistCleaner(TokenBlacklistRepository tokenBlacklistRepository) {
        this.tokenBlacklistRepository = tokenBlacklistRepository;
    }

    @Transactional
    @Scheduled(cron = "0 0 0 L * ?")
    public void clearTokenBlacklist() {
        log.info("Начинаем очистку чёрного списка токенов!");
        tokenBlacklistRepository.deleteAllByExpirationTimeBefore(LocalDateTime.now());
        log.info("Закончили очистку чёрного списка токенов!");
    }
}
