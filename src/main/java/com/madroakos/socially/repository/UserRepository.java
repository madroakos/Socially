package com.madroakos.socially.repository;

import com.madroakos.socially.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
