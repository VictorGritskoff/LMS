package com.courseproject.LMS.services;

import com.courseproject.LMS.models.Course;
import com.courseproject.LMS.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getCoursesWithTeachers() {
        return courseRepository.findAllWithTeacher();
    }

    public void save(Course course) {
        courseRepository.save(course);
    }
}
