package com.courseproject.LMS.controllers;


import com.courseproject.LMS.models.Teacher;
import com.courseproject.LMS.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @GetMapping("/teachers")
    public String getTeachers(Model model){
        List<Teacher> teacherList = teacherService.getTeachers();
        model.addAttribute("teachers", teacherList);
        return "teachers";
    }
    @PostMapping("/teachers/addNew")
    public String addNew(Teacher teacher){
        teacherService.save(teacher);
        return "redirect:/teachers";
    }
    @RequestMapping("/teachers/findById")
    @ResponseBody
    public Optional<Teacher> findById(int id){
        return teacherService.findById(id);
    }

    @RequestMapping(value = "/teachers/update", method = RequestMethod.POST)
    public String update(@RequestParam int id, Teacher teacher){
        teacher.setTeacherId(id);
        teacherService.save(teacher);
        return "redirect:/teachers";
    }
    @RequestMapping(value = "/teachers/delete", method = RequestMethod.POST)
    public String delete(@RequestParam int id) {
        teacherService.deleteById(id);
        return "redirect:/teachers";
    }
}
