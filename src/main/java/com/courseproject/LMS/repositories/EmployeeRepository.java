package com.courseproject.LMS.repositories;

import com.courseproject.LMS.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
