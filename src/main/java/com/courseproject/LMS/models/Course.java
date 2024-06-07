package com.courseproject.LMS.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private long courseId;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @Column(name = "price")
    private double price;

    @Column(name = "course_name")
    private String name;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "description", length = 1000)
    private String description; // Новое поле для описания курса
}