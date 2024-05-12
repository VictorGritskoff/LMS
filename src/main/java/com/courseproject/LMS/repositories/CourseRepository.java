package com.courseproject.LMS.repositories;

import com.courseproject.LMS.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {

        @Query("SELECT c FROM Course c JOIN FETCH c.teacher")
        List<Course> findAllWithTeacher();


}
