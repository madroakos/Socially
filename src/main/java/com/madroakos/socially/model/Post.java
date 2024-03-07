package com.madroakos.socially.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "app_post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String postContent;
    private LocalDateTime timeSubmitted;

    public Post() {
    }

    public Post(String username, String postContent, LocalDateTime timeSubmitted) {
        this.username = username;
        this.postContent = postContent;
        this.timeSubmitted = timeSubmitted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public LocalDateTime getTimeSubmitted() {
        return timeSubmitted;
    }

    public void setTimeSubmitted(LocalDateTime timeSubmitted) {
        this.timeSubmitted = timeSubmitted;
    }
}
