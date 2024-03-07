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

    @GetMapping("/postsByUser") //Request without username should be removed later
    public List<Post> getPostsByUser(@RequestParam(required = true) String username) {
            return postRepository.findByUsername(username);

    }

    @GetMapping("/postsByFollowings") //Request without username should be removed later
    public List<Post> getPostsByUser(@RequestParam(required = true) List<String> username) {
        return postRepository.findByUsernameIn(username);
    }

    @PostMapping("/submitPost")
    public void submitPost(@RequestBody Post post) {
        postRepository.save(post);
    }
}
