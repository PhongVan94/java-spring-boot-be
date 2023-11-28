package io.javabackend.service;

import io.javabackend.entity.Group;
import io.javabackend.entity.Role;
import io.javabackend.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    User addUser(User user);

    List<User> getUserList();

    User updateUser(User user);

    void deleteUserById(int id);

    User getUserById(int id);

    //advance
    List<User> getUserWithPagination(int page, int limit);


    User getUserWithEmailOrPhoneNumber(String valueLogin, String password);

    Optional<User> findByUserName(String username);

    boolean checkEmailExist(String email);

    boolean checkPhoneExist(String phone);

    String hashUserPassword(String password);

    boolean checkPassPassword(String inputPassword, String hashPassword);

    void setGroupDefault (User user);
}
