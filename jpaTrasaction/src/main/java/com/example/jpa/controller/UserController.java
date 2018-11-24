package com.example.jpa.controller;

import com.example.jpa.entity.User;
import com.example.jpa.repository.UserRepository;
import com.example.jpa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * controller
 * <p>
 * 何哥 {@link  RestController}
 * 2018/11/3 17:55
 **/
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("saveUser")
    public User sava(@RequestBody User user) {
        return userService.save(user);
    }

    @PostMapping("saveUserByTraction")
    public User saveTransactional(@RequestBody User user) {
        return userService.saveTransactional(user);
    }

    @GetMapping("user")
    public List<User> getUser() {
        List<User> users = userRepository.findAll();
        return users;
    }
}
