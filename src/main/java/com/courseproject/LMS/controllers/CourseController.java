package com.courseproject.LMS.controllers;

import com.courseproject.LMS.dto.CourseDetailsDTO;
import com.courseproject.LMS.models.Course;
import com.courseproject.LMS.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

//@Controller
//public class CourseController {
//
//    @Autowired
//    private CourseService courseService;
//
//    @GetMapping("/courses")
//    public String getCourses(Model model) {
//
//        // Получаем список курсов с преподавателями
//        List<Course> courseList = courseService.getCoursesWithTeachers();
//
//        // Преобразуем список курсов в список DTO
//        List<CourseDetailsDTO> coursesWithTeachers = courseList.stream()
//                .map(course -> new CourseDetailsDTO(
//                        course.getCourseId(),
//                        course.getName(),
//                        course.getPrice(),
//                        course.getTeacher().getFirstName(),
//                        course.getTeacher().getLastName(),
//                        course.getTeacher().getSurname()))
//                .collect(Collectors.toList());
//
//        // Передаем список курсов с преподавателями в модель
//        model.addAttribute("coursesWithTeachers", coursesWithTeachers);
//
//        // Возвращаем имя представления
//        return "course";
//    }
//
//    @PostMapping("/courses/addNew")
//    public String addNew(Course course) {
//        courseService.save(course);
//        return "redirect:/courses";
//    }
//}
