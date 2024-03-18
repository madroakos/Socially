package com.madroakos.socially.service;

import com.madroakos.socially.repository.TokenBlacklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenCleanupService {

    @Autowired
    private TokenBlacklistRepository tokenBlacklistRepository;

    @Scheduled(cron = "0 0 12 * * ?")
    public void cleanupExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        tokenBlacklistRepository.deleteAllExpiredSince(now);
    }
}
