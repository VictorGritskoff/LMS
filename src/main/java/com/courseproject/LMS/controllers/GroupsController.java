package com.courseproject.LMS.controllers;

import com.courseproject.LMS.models.Client;
import com.courseproject.LMS.models.Course;
import com.courseproject.LMS.models.Group;
import com.courseproject.LMS.models.Order;
import com.courseproject.LMS.services.ClientService;
import com.courseproject.LMS.services.CourseService;
import com.courseproject.LMS.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class GroupsController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ClientService clientService;

    @GetMapping("/groups")
    public String getGroups(Model model){
        List<Group> groupList = groupService.getGroups();
        List<Client> clientList = clientService.getClients();
        List<Course> courseList = courseService.getCourses();
        model.addAttribute("customers", clientList);
        model.addAttribute("courses", courseList);
        model.addAttribute("groups", groupList);
        return "groups";
    }
    @PostMapping("/groups/addNew")
    public String addNew(@RequestParam("clients") List<Integer> clientIds,
                         @RequestParam("course") int courseId,
                         @RequestParam("groupDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate creationDate) {
        Course course = courseService.getCourseById(courseId);
        LocalDate currentDate = LocalDate.now();

        Group group = new Group();
        group.setCourse(course);
        if (creationDate.isBefore(currentDate)) {
            return "redirect:/groups";
        }
        group.setCreationDate(creationDate);

        for (Integer clientId : clientIds) {
            Client client = clientService.getClientById(clientId);
            group.addClient(client);
        }
        if (creationDate.isEqual(currentDate)) {
            group.setStatus("Идет");
        } else if (creationDate.isAfter(currentDate)) {
            group.setStatus("Ожидает");
        }

        groupService.save(group);
        return "redirect:/groups";
    }
    @PostMapping("/groups/update")
    public String updateGroup(@RequestParam("groupId") int groupId,
                              @RequestParam("clients") List<Integer> clientIds,
                              @RequestParam("course") int courseId,
                              @RequestParam("groupDateUpdate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate creationDate) {

        Group group = groupService.getGroupById(groupId);

        Course course = courseService.getCourseById(courseId);
        group.setCourse(course);
        group.setCreationDate(creationDate);

        group.getClients().clear();
        for (Integer clientId : clientIds) {
            Client client = clientService.getClientById(clientId);
            group.addClient(client);
        }

        groupService.save(group);

        return "redirect:/groups";
    }
    @RequestMapping(value = "/groups/delete", method = RequestMethod.POST)
    public String delete(@RequestParam int id) {
        groupService.deleteById(id);
        return "redirect:/groups";
    }
}
