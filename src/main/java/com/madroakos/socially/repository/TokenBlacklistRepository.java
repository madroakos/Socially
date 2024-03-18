package com.madroakos.socially.repository;

import com.madroakos.socially.model.TokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Long> {
    Boolean existsByToken(String token);
}
