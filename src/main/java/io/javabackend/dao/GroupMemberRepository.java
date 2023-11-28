package io.javabackend.dao;

import io.javabackend.entity.GroupMember;
import io.javabackend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember,Integer> {
GroupMember findByName(String name);
}
