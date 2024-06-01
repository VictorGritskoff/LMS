package com.courseproject.LMS.controllers;

import com.courseproject.LMS.models.Employee;
import com.courseproject.LMS.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class SettingsController {
    @Autowired
    EmployeeService employeeService;
    @GetMapping("/settings")
    public String getProfilePage(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            // Получите данные пользователя из базы данных
            Optional<Employee> employeeOptional = employeeService.findByUsername(username);
            if (employeeOptional.isPresent()) {
                Employee employee = employeeOptional.get(); // Получение объекта Employee из Optional
                model.addAttribute("employee", employee);
            }
        }
        return "settings";
    }
}
