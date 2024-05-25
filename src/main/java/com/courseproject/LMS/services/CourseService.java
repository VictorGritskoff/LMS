package com.courseproject.LMS.services;

import com.courseproject.LMS.models.Course;
import com.courseproject.LMS.models.Teacher;
import com.courseproject.LMS.repositories.CourseRepository;
import com.courseproject.LMS.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;
    public List<Course> getCourses(){
        return courseRepository.findAll();
    }

    public void save(Course course){
        courseRepository.save(course);
    }
    public Optional<Course> findById(int id){
        return courseRepository.findById(id);
    }

    public void deleteById(int id) {
        courseRepository.deleteById(id);
    }

    public Course getCourseById(int courseId) {
        return courseRepository.getById(courseId);
    }
    public int getCourseCount() {
        return courseRepository.findAll().size();
    }
}
