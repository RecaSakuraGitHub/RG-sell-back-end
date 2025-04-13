package com.recasakura.sellbackend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recasakura.sellbackend.exception.UnLoginException;
import com.recasakura.sellbackend.exception.UserNotFoundException;
import com.recasakura.sellbackend.model.user.*;
import com.recasakura.sellbackend.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserCreateRequest request, HttpSession session) {
        User user = this.userService.createUser(request);
        session.setAttribute("user", user);
        return ResponseEntity.ok(Map.of("message", "user registered"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request, HttpSession session) {
        User user = this.userService.login(request.getEmail(), request.getPhone());
        session.setAttribute("user", user);
        return ResponseEntity.ok(Map.of("message", "login success"));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(HttpSession session) {
        if ((User) session.getAttribute("user") == null) {
            throw new UnLoginException();
        }
        UserResponse response = new UserResponse((User) session.getAttribute("user"));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody UserDeleteRequest request) {
        User user = this.userService.deleteUser(request);
        return ResponseEntity.ok(Map.of("message", "user: " + user.getName() + " deleted"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse user = this.userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserProjection>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }
}
