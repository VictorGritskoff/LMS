package com.courseproject.LMS.repositories;

import com.courseproject.LMS.models.Order;
import com.courseproject.LMS.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    List<Teacher> findByCreationDateBetween(LocalDateTime startTime, LocalDateTime endTime);
}
