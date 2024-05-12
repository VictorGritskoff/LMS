package com.courseproject.LMS.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnalyticsController {
    @GetMapping("/analytics")
    public String getHome(){
        return "analytics";
    }
}
