package com.courseproject.LMS.services;

import com.courseproject.LMS.models.Teacher;
import com.courseproject.LMS.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

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
    public int getTeacherCount() {
        return teacherRepository.findAll().size();
    }
    // Получение данных для графика "Зарплаты за год" на странице аналитики
    public List<Map<String, BigDecimal>> getSalaryForMonthScale() {
        LocalDate startDate = LocalDate.now().withDayOfMonth(1); // Начало текущего месяца
        LocalDate endDate = startDate.plusMonths(1).minusDays(1); // Конец текущего месяца

        List<Map<String, BigDecimal>> salaryData = new ArrayList<>();

        // Получение зарплат за текущий месяц
        List<Teacher> teachers = teacherRepository.findByCreationDateBetween(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));

        // Группирование зарплат по месяцам
        Map<Integer, BigDecimal> totalSalaryByMonth = new HashMap<>();
        for (Teacher teacher : teachers) {
            int monthNumber = teacher.getCreationDate().getMonthValue();
            totalSalaryByMonth.put(monthNumber, totalSalaryByMonth.getOrDefault(monthNumber, BigDecimal.ZERO).add(teacher.getSalary()));
        }

        // Формирование данных для возврата
        for (int monthNumber = 1; monthNumber <= 12; monthNumber++) {
            Map<String, BigDecimal> dataPoint = new HashMap<>();
            dataPoint.put("month", BigDecimal.valueOf(monthNumber));
            dataPoint.put("totalSalary", totalSalaryByMonth.getOrDefault(monthNumber, BigDecimal.ZERO));
            salaryData.add(dataPoint);
        }

        return salaryData;
    }
}
