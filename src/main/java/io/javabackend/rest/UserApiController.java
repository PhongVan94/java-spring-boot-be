package io.javabackend.rest;

import io.javabackend.entity.User;
import io.javabackend.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserApiController {
    private UserService userService;

    @Autowired
    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public List<User> getAllUsers() {
        return userService.getUserList();
    }

    @GetMapping("/read")
    public Map<String, Object> getUserWithPagination(@RequestParam int page, @RequestParam int limit) {
        Map<String, Object> response = new HashMap<>();
        List<User> users = null;
        try {
            users = userService.getUserWithPagination(page, limit);
            int totalUser = userService.getUserList().size();
            if (users != null) {
                int totalPages = (int) Math.ceil(totalUser / (double) limit);

                Map<String, Object> data = new HashMap<>();
                data.put("totalRows", users.size());
                data.put("totalPages", totalPages);
                data.put("users", users);

                response.put("EC", 0);
                response.put("EM", "GET DATA SUCCESS");
                response.put("DT", data);
                response.put("status", 200);
            } else {
                response.put("EC", 0);
                response.put("EM", "NOT FOUND ANY USER");
                response.put("DT", null);
                response.put("status", 200);
            }
        } catch (Exception e) {
            response.put("EC", -1);
            response.put("EM", "SOMETHING WENT WRONG IN SERVER");
            response.put("DT", null);
            response.put("status", 500);
            throw new RuntimeException(e);
        }

        return response;
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/delete/{id}")
    @Transactional
    public Map<String, Object> deleteUser(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        try {
            User existUser = userService.getUserById(id);
            if (existUser != null) {
                userService.deleteUserById(id);
                response.put("EC", 0);
                response.put("EM", "DELETE USER SUCCESS");
                response.put("DT", null);
                response.put("status", 200);
            } else {
                response.put("EC", 0);
                response.put("EM", "USER NOT EXIST");
                response.put("DT", null);
                response.put("status", 200);
            }
        } catch (Exception e) {
            response.put("EC", -1);
            response.put("EM", "SOMETHING WENT WRONG IN SERVER");
            response.put("DT", null);
            response.put("status", 500);
            throw new RuntimeException(e);
        }
        return response;
    }
}
