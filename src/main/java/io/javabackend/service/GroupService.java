package io.javabackend.service;

import io.javabackend.entity.Group;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GroupService {
    Group addGroupMember(Group group);

    List<Group> getGroupMemberList();

    Group updateGroupMember(Group group);

    void deleteGroupMemberById(int id);

    Group getGroupMemberById(int id);

    //advance
    List<Group>  getGroupMemberWithPagination(int page, int limit);

}
