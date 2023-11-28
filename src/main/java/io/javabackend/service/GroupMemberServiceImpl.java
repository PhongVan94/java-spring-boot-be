package io.javabackend.service;

import io.javabackend.dao.GroupMemberRepository;
import io.javabackend.entity.GroupMember;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupMemberServiceImpl implements GroupMemberService {

    private GroupMemberRepository groupMemberRepository;

    @Autowired
    public GroupMemberServiceImpl(GroupMemberRepository groupMemberRepository) {
        this.groupMemberRepository = groupMemberRepository;
    }

    @Override
    @Transactional
    public GroupMember addGroupMember(GroupMember groupMember) {

        return groupMemberRepository.save(groupMember);
    }

    @Override
    public List<GroupMember> getGroupMemberList() {
        return groupMemberRepository.findAll();
    }

    @Override
    @Transactional
    public GroupMember updateGroupMember(GroupMember groupMember) {
        return groupMemberRepository.saveAndFlush(groupMember);
    }

    @Override
    @Transactional
    public void deleteGroupMemberById(int id) {
        groupMemberRepository.deleteById(id);
    }

    @Override
    public GroupMember getGroupMemberById(int id) {
        return groupMemberRepository.getById(id);
    }

    @Override
    public List<GroupMember> getGroupMemberWithPagination(int page, int limit) {

        // Create a PageRequest for the given page and limit
        PageRequest pageRequest = PageRequest.of(page, limit);

        // Use groupMemberRepository to fetch groupMembers with pagination
        Page<GroupMember> groupMemberPage = groupMemberRepository.findAll(pageRequest);

        // Return the list of groupMembers from the Page
        return groupMemberPage.getContent();

    }
}


