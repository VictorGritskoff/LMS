package com.courseproject.LMS.controllers;

import com.courseproject.LMS.models.Employee;
import com.courseproject.LMS.models.Language;
import com.courseproject.LMS.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register/user")
    public ResponseEntity<?> createUser(@RequestBody Employee employee) {
        // Проверка наличия пользователя с таким же username в базе данных
        if (employeeRepository.findByUsername(employee.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }
        else if (employeeRepository.findByEmail(employee.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email is already taken!");
        }

        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employee.setLanguage(Language.RU);
        employee.setRole("ADMIN");
        employeeRepository.save(employee);
        return ResponseEntity.ok("User registered successfully!");
    }
}