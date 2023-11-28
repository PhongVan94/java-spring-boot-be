package io.javabackend.service;

import io.javabackend.dao.GroupRepository;
import io.javabackend.entity.Group;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private GroupRepository groupRepository;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    @Transactional
    public Group addGroupMember(Group group) {

        return groupRepository.save(group);
    }

    @Override
    public List<Group> getGroupMemberList() {
        return groupRepository.findAll();
    }

    @Override
    @Transactional
    public Group updateGroupMember(Group group) {
        return groupRepository.saveAndFlush(group);
    }

    @Override
    @Transactional
    public void deleteGroupMemberById(int id) {
        groupRepository.deleteById(id);
    }

    @Override
    public Group getGroupMemberById(int id) {
        return groupRepository.getById(id);
    }

    @Override
    public List<Group> getGroupMemberWithPagination(int page, int limit) {

        // Create a PageRequest for the given page and limit
        PageRequest pageRequest = PageRequest.of(page, limit);

        // Use groupMemberRepository to fetch groupMembers with pagination
        Page<Group> groupMemberPage = groupRepository.findAll(pageRequest);

        // Return the list of groupMembers from the Page
        return groupMemberPage.getContent();

    }
}


