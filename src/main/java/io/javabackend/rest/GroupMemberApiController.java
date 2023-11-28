package io.javabackend.rest;

import io.javabackend.entity.GroupMember;
import io.javabackend.entity.User;
import io.javabackend.service.GroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/group")
public class GroupMemberApiController {

    private GroupMemberService groupMemberService;
    @Autowired
    public GroupMemberApiController(GroupMemberService groupMemberService) {
        this.groupMemberService = groupMemberService;
    }


    @GetMapping("/read")
    public Map<String, Object> getGroupMemberList() {
        Map<String, Object> response = new HashMap<>();
        List<GroupMember> groupMembers = null;
        try {
            groupMembers = groupMemberService.getGroupMemberList();
            if (groupMembers != null) {


                Map<String, Object> data = new HashMap<>();
                data.put("groupMembers", groupMembers);

                response.put("EC", 0);
                response.put("EM", "GET GROUP SUCCESS");
                response.put("DT", data);
                response.put("status", 200);
            } else {
                response.put("EC", 0);
                response.put("EM", "NOT FOUND ANY GROUP");
                response.put("DT", null);
                response.put("status", 200);
            }
        } catch (Exception e) {
            response.put("EC", -1);
            response.put("EM", "SOMETHING WENT WRONG IN SERVER");
            response.put("DT", null);
            response.put("status", 500);
            throw new RuntimeException(e);
        }

        return response;
    }


}
