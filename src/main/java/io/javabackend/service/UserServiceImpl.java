package io.javabackend.service;

import io.javabackend.dao.UserRepository;
import io.javabackend.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.relational.core.sql.SelectBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public User addUser(User user) {

        return userRepository.save(user);
    }

    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.getById(id);
    }

    @Override
    public List<User> getUserWithPagination(int page, int limit) {

        // Create a PageRequest for the given page and limit
        PageRequest pageRequest = PageRequest.of(page, limit);

        // Use userRepository to fetch users with pagination
        Page<User> userPage = userRepository.findAll(pageRequest);

        // Return the list of users from the Page
        return userPage.getContent();

    }
}


