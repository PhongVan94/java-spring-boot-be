package io.javabackend.rest;

import io.javabackend.entity.GroupMember;
import io.javabackend.entity.User;
import io.javabackend.service.GroupMemberService;
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
    private GroupMemberService groupMemberService;

    @Autowired
    public UserApiController(UserService userService, GroupMemberService groupMemberService) {
        this.userService = userService;
        this.groupMemberService = groupMemberService;
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
                response.put("EC", -1);
                response.put("EM", "NOT FOUND ANY USER");
                response.put("DT", null);
                response.put("status", 404);
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

    @PostMapping("/create")
    @Transactional
    public Map<String, Object> createUser(@RequestBody Map<String, String> data) {
        String email = data.get("email");
        String phone = data.get("phone");
        String username = data.get("username");
        String password = data.get("password");
        String address = data.get("address");
        String gender = data.get("gender");
        String groupId = data.get("groupId");

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
            user.setGender(gender);
            user.setAddress(address);
            GroupMember group = groupMemberService.getGroupMemberById(Integer.parseInt(groupId));
            if (group != null) {
                user.setGroupMember(group);
                userService.addUser(user);
                responseMap.put("EC", 0);
                responseMap.put("EM", "CREATE USER SUCCESS");
                responseMap.put("DT", null);
                responseMap.put("status", 200);
            } else {
                responseMap.put("EC", -1);
                responseMap.put("EM", "NOT FOUND GROUP");
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

    @PutMapping("/update")
    @Transactional
    public Map<String, Object> updateUser(@RequestBody Map<String, Object> data) {
        String id = data.get("id").toString();
        String username = data.get("username").toString();
        String address = data.get("address").toString();
        String gender = data.get("gender").toString();
        String groupId = data.get("groupId").toString();
        Map<String, Object> responseMap = new HashMap<>();
        try {
            User user = userService.getUserById(Integer.parseInt(id));

            System.out.println("check");

            if (user != null) {
                user.setUsername(username);
                GroupMember group = groupMemberService.getGroupMemberById(Integer.parseInt(groupId));

                System.out.println("check");

                if (group != null) {

                    user.setGroupMember(group);
                    user.setGender(gender);
                    user.setAddress(address);
                    userService.updateUser(user);
                    responseMap.put("EC", 0);
                    responseMap.put("EM", "UPDATE USER SUCCESS");
                    responseMap.put("DT", null);
                    responseMap.put("status", 200);
                } else {
                    responseMap.put("EC", -1);
                    responseMap.put("EM", "NOT FOUND GROUP");
                    responseMap.put("DT", null);
                    responseMap.put("status", 404);
                }

            } else {
                responseMap.put("EC", -1);
                responseMap.put("EM", "NOT FOUND USER");
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
}
