package com.madroakos.socially.controller;

import com.madroakos.socially.model.User;
import com.madroakos.socially.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public void createUser(@RequestBody User user) {
        userRepository.save(user);
    }

    @GetMapping("/searchForUser")
    public List<User> searchForUser(@RequestParam String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username);
    }
}
