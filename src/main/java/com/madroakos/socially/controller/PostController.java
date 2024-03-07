package com.madroakos.socially.controller;

import com.madroakos.socially.model.Post;
import com.madroakos.socially.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {
    private final PostRepository postRepository;

    @Autowired
    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/post")
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    @PostMapping("/post")
    public void submitPost(@RequestBody Post post) {
        postRepository.save(post);
    }
}
