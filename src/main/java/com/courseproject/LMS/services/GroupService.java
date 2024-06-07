package com.courseproject.LMS.services;

import com.courseproject.LMS.models.Group;
import com.courseproject.LMS.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    public List<Group> getGroups(){
        return groupRepository.findAll();
    }
    public void save(Group group){
        groupRepository.save(group);
    }
    public void deleteById(int id){
        groupRepository.deleteById(id);
    }
    public Group getGroupById(int groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found with id: " + groupId));
    }
}
