package com.madroakos.socially.controller;

import com.madroakos.socially.model.Post;
import com.madroakos.socially.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:63343")
@RestController
public class PostController {
    private final PostRepository postRepository;

    @Autowired
    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;

        fillWithPostsForTesting();
    }

    private void fillWithPostsForTesting() {
        for (int i = 0; i < 15; i++) {
            postRepository.save(new Post("user" + i, i + ". epic comment here"));
        }
    }

    @GetMapping("/posts")
    public List<Post> getPostsByUser() {
        return postRepository.findAll();
    }

    @GetMapping("/postsByUser")
    public List<Post> getPostsByUser(@RequestParam(required = true) String username) {
            return postRepository.findByUsername(username);
    }

    @GetMapping("/postsByFollowings")
    public List<Post> getPostsByUser(@RequestParam(required = true) List<String> username) {
        return postRepository.findByUsernameIn(username);
    }

    @PostMapping("/submitPost")
    public void submitPost(@RequestBody Post post) {
        postRepository.save(post);
    }
}
