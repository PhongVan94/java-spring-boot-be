package io.javabackend.service;

import io.javabackend.entity.GroupMember;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GroupMemberService {
    GroupMember addGroupMember(GroupMember groupMember);

    List<GroupMember> getGroupMemberList();

    GroupMember updateGroupMember(GroupMember groupMember);

    void deleteGroupMemberById(int id);

    GroupMember getGroupMemberById(int id);

    //advance
    List<GroupMember>  getGroupMemberWithPagination(int page, int limit);

}
