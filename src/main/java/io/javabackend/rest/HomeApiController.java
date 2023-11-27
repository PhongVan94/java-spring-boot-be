package io.javabackend.rest;

import io.javabackend.entity.Role;
import io.javabackend.entity.User;
//import io.javabackend.jwt.filter.JwtAuthFilter;
import io.javabackend.jwt.service.JwtService;
import io.javabackend.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.DefaultCookieSerializerCustomizer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class HomeApiController {

    private UserService userService;
    private JwtService jwtService;

    @Autowired

    public HomeApiController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }


    @PostMapping("/login")
    public Map<String, Object> handleUserLogin(
            @RequestBody Map<String, String> dataLogin,
            HttpServletResponse response) {

        String valueLogin = dataLogin.get("valueLogin");
        String password = dataLogin.get("password");

        Map<String, Object> responseMap = new HashMap<>();
        try {
            User user = userService.getUserWithEmailOrPhoneNumber(valueLogin, password);

            if (user != null) {
                Map<String, Object> payload = new HashMap<>();
                payload.put("email", user.getEmail());
                payload.put("roles", user.getGroupMember().getRoles());
                payload.put("username", user.getUsername());

                String token = jwtService.createToken(payload, user.getUsername());

                Map<String, Object> data = new HashMap<>();
                data.put("email", user.getEmail());
                data.put("username", user.getUsername());
                data.put("group", user.getGroupMember());
                data.put("token", token);

                responseMap.put("EC", 0);
                responseMap.put("EM", "GET DATA SUCCESS");
                responseMap.put("DT", data);
                responseMap.put("status", 200);

                // Set cookie
                Cookie cookie = new Cookie("jwt", token);
                cookie.setMaxAge(60 * 60); // 1 hour
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                response.addCookie(cookie);
            } else {
                responseMap.put("EC", -1);
                responseMap.put("EM", "YOUR EMAIL/PHONE NUMBER OR PASSWORD IS INCORRECT");
                responseMap.put("DT", null);
                responseMap.put("status", 404);
            }
        } catch (Exception e) {
            responseMap.put("EC", -1);
            responseMap.put("EM", "SOMETHING WENT WRONG IN SERVER");
            responseMap.put("DT", null);
            responseMap.put("status", 500);
            throw new RuntimeException(e);
        }
        return responseMap;
    }

    @PostMapping("/register")
    public Map<String, Object> handleRegister(
            @RequestBody Map<String, String> data) {

        String email = data.get("email");
        String phone = data.get("phone");
        String username = data.get("username");
        String password = data.get("password");

        Map<String, Object> responseMap = new HashMap<>();
        try {
            if (userService.checkEmailExist(email)) {
                responseMap.put("EC", -1);
                responseMap.put("EM", "THE EMAIL IS ALREADY EXIST");
                responseMap.put("DT", "email");
                responseMap.put("status", 400);
                return responseMap;

            }
            if (userService.checkPhoneExist(phone)) {
                responseMap.put("EC", -1);
                responseMap.put("EM", "THE PHONE NUMBER IS ALREADY EXIST");
                responseMap.put("DT", "phone");
                responseMap.put("status", 400);
                return responseMap;
            }
            String hashPassword = userService.hashUserPassword(password);
            User user = new User();
            user.setEmail(email);
            user.setPhone(phone);
            user.setUsername(username);
            user.setPassword(hashPassword);
            userService.addUser(user);

            responseMap.put("EC", 0);
            responseMap.put("EM", "CREATE USER SUCCESS");
            responseMap.put("DT", null);
            responseMap.put("status", 200);


        } catch (Exception e) {
            responseMap.put("EC", -1);
            responseMap.put("EM", "SOMETHING WENT WRONG IN SERVER");
            responseMap.put("DT", null);
            responseMap.put("status", 500);

            throw new RuntimeException(e);
        }
        return responseMap;
    }

}
