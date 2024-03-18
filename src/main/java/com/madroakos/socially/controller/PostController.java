package com.madroakos.socially.controller;

import com.madroakos.socially.dto.PostContent;
import com.madroakos.socially.model.Post;
import com.madroakos.socially.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class PostController {
    private final PostRepository postRepository;
    private final Sort sortByTimeDescending = Sort.by(Sort.Direction.DESC, "timeSubmitted");

    @Autowired
    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    @GetMapping("/posts")
    public List<Map<String, Object>> getPosts() {
        List<Post> temp = postRepository.findAll(sortByTimeDescending);
        LocalDateTime currentTime = LocalDateTime.now();

        return temp.stream().map(post -> {
            Map<String, Object> postMap = new HashMap<>();
            Duration timeSince = Duration.between(post.getTimeSubmitted(), currentTime);
            String timeSinceInString = simpleTimeFormat(timeSince);

            postMap.put("id", post.getId());
            postMap.put("username", post.getUsername());
            postMap.put("postContent", post.getPostContent());
            postMap.put("timeSince", timeSinceInString);
            return postMap;
        }).collect(Collectors.toList());
    }

    private String simpleTimeFormat(Duration time) {
        if (time.compareTo(Duration.ofMinutes(1)) < 0) {
            return "<1m";
        } else if ((time.compareTo(Duration.ofMinutes(1)) >= 0) && time.compareTo(Duration.ofMinutes(60)) < 0) {
            return (time.getSeconds() / 60) + "m";
        } else {
            return (time.getSeconds() / 3600) + "h";
        }
    }

    @GetMapping("/postsByUser")
    public List<Map<String, Object>> getPostsByUser(@RequestParam() String username) {
        List<Post> temp = postRepository.findByUsernameOrderByTimeSubmittedDesc(username);
        LocalDateTime currentTime = LocalDateTime.now();

        return temp.stream().map(post -> {
            Map<String, Object> postMap = new HashMap<>();
            Duration timeSince = Duration.between(post.getTimeSubmitted(), currentTime);
            String timeSinceInString = simpleTimeFormat(timeSince);

            postMap.put("id", post.getId());
            postMap.put("username", post.getUsername());
            postMap.put("postContent", post.getPostContent());
            postMap.put("timeSince", timeSinceInString);
            return postMap;
        }).collect(Collectors.toList());
    }

    @PostMapping("/submitPost")
    public ResponseEntity<String> submitPost(@RequestBody PostContent postContent) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String authenticatedUsername = userDetails.getUsername();
        Post currentPost = new Post();
        currentPost.setUsername(authenticatedUsername);
        currentPost.setPostContent(postContent.getPostContent());
        currentPost.setTimeSubmitted(LocalDateTime.now());
        postRepository.save(currentPost);
        return ResponseEntity.status(HttpStatus.CREATED).body("Post submitted successfully");
    }
}
