package com.courseproject.LMS.services;

import com.courseproject.LMS.models.Teacher;
import com.courseproject.LMS.repositories.TeacherRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class TeacherService {
    private Double currentMROT;
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
        LocalDate startDate = LocalDate.now().withDayOfYear(1); // Начало текущего года
        LocalDate currentDate = LocalDate.now(); // Текущая дата

        List<Map<String, BigDecimal>> salaryData = new ArrayList<>();

        // Получение зарплат за текущий год до текущей даты
        List<Teacher> teachers = teacherRepository.findByCreationDateBetween(startDate.atStartOfDay(), currentDate.atTime(23, 59, 59));

        // Группирование зарплат по месяцам
        Map<Integer, BigDecimal> totalSalaryByMonth = new HashMap<>();
        for (Teacher teacher : teachers) {
            int monthNumber = teacher.getCreationDate().getMonthValue();
            totalSalaryByMonth.put(monthNumber, totalSalaryByMonth.getOrDefault(monthNumber, BigDecimal.ZERO).add(teacher.getSalary()));
        }

        // Накопительные зарплаты до текущего месяца
        BigDecimal cumulativeSalary = BigDecimal.ZERO;
        for (int monthNumber = 1; monthNumber <= currentDate.getMonthValue(); monthNumber++) {
            cumulativeSalary = cumulativeSalary.add(totalSalaryByMonth.getOrDefault(monthNumber, BigDecimal.ZERO));
            Map<String, BigDecimal> dataPoint = new HashMap<>();
            dataPoint.put("month", BigDecimal.valueOf(monthNumber));
            dataPoint.put("totalSalary", cumulativeSalary);
            salaryData.add(dataPoint);
        }

        return salaryData;
    }

    public List<Map<String, Object>> getExpensesForTimeScale() {
        LocalDate startDate = LocalDate.now().withDayOfYear(1);
        LocalDate currentDate = LocalDate.now();

        List<Map<String, Object>> expenses = new ArrayList<>();

        // Получение зарплат за текущий год до текущей даты
        List<Teacher> teachers = teacherRepository.findByCreationDateBetween(startDate.atStartOfDay(), currentDate.atTime(23, 59, 59));

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

    public Double getCurrentMROT(){
        try {
            String url = "https://myfin.by/wiki/term/minimalnaya-zarabotnaya-plata";
            Document doc = Jsoup.connect(url).get();
            Element mzpElement = doc.selectFirst(".information-block__current-value.x__current-value--mr");

            String mzpText = mzpElement.text();

            Double mzpValue = Double.parseDouble(mzpText.replaceAll("[^0-9.]+", ""));

            return mzpValue;

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }
}
