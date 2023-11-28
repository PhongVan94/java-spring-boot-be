package io.javabackend.rest;

import io.javabackend.entity.Group;
import io.javabackend.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/group")
public class GroupApiController {

    private GroupService groupService;
    @Autowired
    public GroupApiController(GroupService groupService) {
        this.groupService = groupService;
    }


    @GetMapping("/read")
    public Map<String, Object> getGroupMemberList() {
        Map<String, Object> response = new HashMap<>();
        List<Group> groups = null;
        try {
            groups = groupService.getGroupMemberList();
            if (groups != null) {

                response.put("EC", 0);
                response.put("EM", "GET GROUP SUCCESS");
                response.put("DT", groups);
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
