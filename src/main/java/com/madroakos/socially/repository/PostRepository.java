package com.madroakos.socially.repository;

import com.madroakos.socially.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUsername(String username);

}
