package com.courseproject.LMS.services;

import com.courseproject.LMS.models.Teacher;
import com.courseproject.LMS.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
    public List<Map<String, Object>> getExpensesForTimeScale() {
        LocalDate startDate = LocalDate.now().withDayOfMonth(1); // Начало текущего месяца
        LocalDate endDate = startDate.plusMonths(1).minusDays(1); // Конец текущего месяца

        List<Map<String, Object>> expenses = new ArrayList<>();

        // Получение зарплат за текущий месяц
        List<Teacher> teachers = teacherRepository.findByCreationDateBetween(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));

        // Группирование зарплат по времени добавления в базу данных и суммирование общего дохода
        Map<LocalDateTime, BigDecimal> totalSalaryByTime = new TreeMap<>();
        for (Teacher teacher : teachers) {
            LocalDateTime formattedDate = teacher.getCreationDate().truncatedTo(ChronoUnit.MINUTES);
            totalSalaryByTime.put(formattedDate, totalSalaryByTime.getOrDefault(formattedDate, BigDecimal.ZERO).add(teacher.getSalary()));
        }

        // Вычисление накопительной суммы
        BigDecimal cumulativeSalary = BigDecimal.ZERO;
        for (Map.Entry<LocalDateTime, BigDecimal> entry : totalSalaryByTime.entrySet()) {
            cumulativeSalary = cumulativeSalary.add(entry.getValue());
            Map<String, Object> dataPoint = new HashMap<>();
            LocalDateTime time = entry.getKey();
            String formattedTime = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            dataPoint.put("time", formattedTime);
            dataPoint.put("totalSalary", cumulativeSalary);
            expenses.add(dataPoint);
        }

        return expenses;
    }
}
