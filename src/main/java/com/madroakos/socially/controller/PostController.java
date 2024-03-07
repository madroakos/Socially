package com.madroakos.socially.controller;

import com.madroakos.socially.model.Post;
import com.madroakos.socially.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    private final PostRepository postRepository;

    @Autowired
    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/post") //Request without username should be removed later
    public List<Post> getPosts(@RequestParam(required = false) String username) {
        if (username != null) {
            return postRepository.findByUsername(username);
        } else {
            return postRepository.findAll();
        }
    }


    @PostMapping("/post")
    public void submitPost(@RequestBody Post post) {
        postRepository.save(post);
    }
}
