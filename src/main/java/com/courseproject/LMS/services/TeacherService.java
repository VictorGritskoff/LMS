package com.courseproject.LMS.services;

import com.courseproject.LMS.models.Teacher;
import com.courseproject.LMS.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    public List<Teacher> getTeachers(){
        return teacherRepository.findAll();
    }

    public void save(Teacher teacher){
        teacherRepository.save(teacher);
    }
    public Optional<Teacher> findById(int id){
        return teacherRepository.findById(id);
    }

    public void deleteById(int id) {
        teacherRepository.deleteById(id);
    }
}
