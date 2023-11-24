package io.javabackend.service;

import io.javabackend.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User addUser(User user);

    List<User> getUserList();

    User updateUser(User user);

    void deleteUserById(int id);

    User getUserById(int id);

    //advance
    List<User>  getUserWithPagination(int page, int limit);

}
