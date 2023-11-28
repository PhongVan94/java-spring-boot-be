package io.javabackend.service;

import io.javabackend.dao.GroupMemberRepository;
import io.javabackend.dao.UserRepository;
import io.javabackend.entity.GroupMember;
import io.javabackend.entity.Role;
import io.javabackend.entity.User;
import io.javabackend.jwt.config.SecurityConfig;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.relational.core.sql.SelectBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private GroupMemberRepository groupMemberRepository;

    private SecurityConfig securityConfig;

    @Autowired

    public UserServiceImpl(UserRepository userRepository, GroupMemberRepository groupMemberRepository, SecurityConfig securityConfig) {
        this.userRepository = userRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.securityConfig = securityConfig;
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


    @Override
    public User getUserWithEmailOrPhoneNumber(String valueLogin, String password) {
        User user = null;
        try {
            user = userRepository.findUserByEmail(valueLogin);
            if (user == null) {
                user = userRepository.findUserByPhone(valueLogin);
                if (user.getPassword().equals(password)) {
                    return user;
                } else {
                    return null;
                }
            } else {
                if (user.getPassword().equals(password)) {
                    return user;
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Optional<User> findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public String hashUserPassword(String password) {
        return securityConfig.passwordEncoder().encode(password);
    }

    @Override
    public boolean checkPassPassword(String inputPassword, String hashPassword) {
        return securityConfig.passwordEncoder().matches(inputPassword,hashPassword);
    }

    @Override
    public void setGroupDefault(User user) {
        GroupMember groupMemberDefault = groupMemberRepository.findByName("GUEST");
        if (groupMemberDefault == null){
            groupMemberDefault = new GroupMember(0,"GUEST","FOR GUEST",null,null);
            groupMemberRepository.save(groupMemberDefault);
        }
        user.setGroupMember(groupMemberDefault);
    }

    @Override
    public boolean checkEmailExist(String email) {
        User user = userRepository.findUserByEmail(email);
        return user != null;

    }

    @Override
    public boolean checkPhoneExist(String phone) {
        User user = userRepository.findUserByPhone(phone);
        return user != null;
    }


}


