package com.courseproject.LMS.controllers;

import com.courseproject.LMS.models.Employee;
import com.courseproject.LMS.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class SettingsController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/settings")
    public ResponseEntity<Employee> getProfilePage() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            Optional<Employee> employeeOptional = employeeService.findByUsername(username);
            if (employeeOptional.isPresent()) {
                return ResponseEntity.ok(employeeOptional.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("/settings")
    public ResponseEntity<Employee> updateProfile(
            @RequestPart("employee") Employee updatedEmployee,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            Optional<Employee> employeeOptional = employeeService.findByUsername(username);
            if (employeeOptional.isPresent()) {
                Employee employee = employeeOptional.get();
                employee.setFirstName(updatedEmployee.getFirstName());
                employee.setLastName(updatedEmployee.getLastName());
                employee.setSurname(updatedEmployee.getSurname());
                employee.setEmail(updatedEmployee.getEmail());
                employee.setUsername(updatedEmployee.getUsername());
                employee.setPassword(updatedEmployee.getPassword());
                if (imageFile != null && !imageFile.isEmpty()) {
                    String imagePath = saveImage(imageFile);
                    employee.setImagePath(imagePath);
                }
                employeeService.save(employee);
                return ResponseEntity.ok(employee);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private String saveImage(MultipartFile imageFile) {
        try {
            String uploadDir = "uploads/"; // Папка для загрузок должна быть доступна через веб-сервер
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }
            String originalFilename = imageFile.getOriginalFilename();
            String timestamp = String.valueOf(System.currentTimeMillis());
            String newFilename = timestamp + "_" + originalFilename;
            String filePath = uploadDirFile.getAbsolutePath() + "/" + newFilename;
            File dest = new File(filePath);
            imageFile.transferTo(dest);
            return uploadDir + newFilename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + imageFile.getOriginalFilename(), e);
        }
    }

}
