package io.javabackend.dao;

import io.javabackend.entity.GroupMember;
import io.javabackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User,Integer> {

    User findUserByEmail(String valueLogin);

    User findUserByPhone(String valueLogin);

    Optional<User> findByUsername(String username);
}
