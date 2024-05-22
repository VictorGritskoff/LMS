package com.courseproject.LMS.controllers;

import com.courseproject.LMS.models.Course;
import com.courseproject.LMS.models.Teacher;
import com.courseproject.LMS.services.CourseService;
import com.courseproject.LMS.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;
    @GetMapping("/courses")
    public String getCourses(Model model){
        List<Teacher> teacherList = teacherService.getTeachers();
        List<Course> courseList = courseService.getCourses();
        model.addAttribute("teachers", teacherList);
        model.addAttribute("courses", courseList);
        return "courses";
    }
    @PostMapping("/courses/addNew")
    public String addNew(Course course){
        courseService.save(course);
        return "redirect:/courses";
    }
    @RequestMapping("/courses/findById")
    @ResponseBody
    public Optional<Course> findById(int id){
        return courseService.findById(id);
    }

    @RequestMapping(value = "/courses/update", method = RequestMethod.POST)
    public String update(@RequestParam int id, Course course){
        course.setCourseId(id);
        courseService.save(course);
        return "redirect:/courses";
    }
    @RequestMapping(value = "/courses/delete", method = RequestMethod.POST)
    public String delete(@RequestParam int id) {
        courseService.deleteById(id);
        return "redirect:/courses";
    }
}

