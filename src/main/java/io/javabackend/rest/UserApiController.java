package io.javabackend.rest;

import io.javabackend.entity.User;
import io.javabackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserApiController {
    private UserService userService;

    @Autowired
    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/abc")
    public String abc() {
        return "abc";
    }

    @GetMapping("/read")
    public List<User> getAllUsers() {
        return userService.getUserList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User User = userService.getUserById(id);
        if (User != null) {
            return ResponseEntity.ok(User);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
