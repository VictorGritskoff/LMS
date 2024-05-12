package com.courseproject.LMS.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TeacherController {
    @GetMapping("/teachers")
    public String getHome(){
        return "teachers";
    }
}
