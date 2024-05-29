package com.courseproject.LMS.services;

import com.courseproject.LMS.models.Order;
import com.courseproject.LMS.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private TeacherService teacherService;
    public List<Order> getOrders(){
        return orderRepository.findAll();
    }

    public void save(Order order){
        orderRepository.save(order);
    }
    public Optional<Order> findById(int id){
        return orderRepository.findById(id);
    }

    public void deleteById(int id) {
        orderRepository.deleteById(id);
    }
    public double getProfit() {
        List<Order> orders = getOrders();
        double profit = 0;
        for (Order order : orders) {
            profit += order.getOrderPrice();
        }
        return profit;
    }

    // Получение данных для графика на странице index
    public List<Map<String, Object>> getProfitDataForChart() {
        LocalDate startDate = LocalDate.now().withDayOfYear(1); // Начало текущего года
        LocalDate endDate = startDate.plusYears(1).minusDays(1); // Конец текущего года

        List<Map<String, Object>> profitData = new ArrayList<>();

        // Получить заказы в заданном временном промежутке
        List<Order> orders = orderRepository.findByOrderTimeBetween(
                startDate.atStartOfDay(), endDate.atTime(23, 59, 59));

        // Группировать заказы по дате и вычислять суммарную прибыль для каждой даты
        Map<LocalDate, Double> profitByDate = orders.stream()
                .collect(Collectors.groupingBy(
                        order -> order.getOrderTime().toLocalDate(),
                        Collectors.summingDouble(Order::getOrderPrice)
                ));

        // Преобразовать данные в формат, подходящий для передачи на график
        profitByDate.forEach((date, profit) -> {
            Map<String, Object> dataPoint = new HashMap<>();
            dataPoint.put("orderTime", date);
            dataPoint.put("profit", profit);
            profitData.add(dataPoint);
        });

        return profitData;
    }
    public List<Order> getRecentOrders() {
        return orderRepository.findTop6ByOrderByOrderTimeDesc();
    }

    // Получение данных для графика "Заказы в месяц" на странице аналитики
    public List<Map<String, Integer>> getCourseCountForSparkChart() {
        LocalDate startDate = LocalDate.now().withDayOfMonth(1); // Начало текущего месяца
        LocalDate endDate = startDate.plusMonths(1).minusDays(1); // Конец текущего месяца

        List<Map<String, Integer>> courseCountData = new ArrayList<>();

        // Получение заказов за текущий месяц
        List<Order> orders = orderRepository.findByOrderTimeBetween(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));

        // Группирование заказов по неделям и подсчет количества курсов
        Map<Integer, Integer> courseCountByWeek = new HashMap<>();
        for (Order order : orders) {
            int weekNumber = getWeekNumber(order.getOrderTime());
            courseCountByWeek.put(weekNumber, courseCountByWeek.getOrDefault(weekNumber, 0) + 1);
        }

        // Формирование данных для возврата
        for (int weekNumber = 1; weekNumber <= 4; weekNumber++) {
            Map<String, Integer> dataPoint = new HashMap<>();
            dataPoint.put("week", weekNumber);
            dataPoint.put("courseCount", courseCountByWeek.getOrDefault(weekNumber, 0));
            courseCountData.add(dataPoint);
        }

        return courseCountData;
    }

    private int getWeekNumber(LocalDateTime date) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return date.get(weekFields.weekOfMonth());
    }

    // Получение данных для графика "Продажи за год" на странице аналитики
    public List<Map<String, Double>> getSalesForYearChart() {
        // Получение начальной и конечной даты для текущего года
        LocalDate startDate = LocalDate.now().withDayOfYear(1); // Начало текущего года
        LocalDate endDate = startDate.plusYears(1).minusDays(1); // Конец текущего года

        List<Map<String, Double>> revenueData = new ArrayList<>();

        // Получение заказов за текущий год
        List<Order> orders = orderRepository.findByOrderTimeBetween(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));

        // Группирование заказов по месяцам и подсчет общего дохода
        Map<Integer, Double> salesByMonth = new HashMap<>();
        for (Order order : orders) {
            int month = order.getOrderTime().getMonthValue();
            double sales = order.getOrderPrice();
            salesByMonth.put(month, salesByMonth.getOrDefault(month, 0.0) + sales);
        }

        // Формирование данных для возврата
        for (int month = 1; month <= 12; month++) {
            Map<String, Double> dataPoint = new HashMap<>();
            dataPoint.put("month", (double) month);
            dataPoint.put("sales", salesByMonth.getOrDefault(month, 0.0));
            revenueData.add(dataPoint);
        }

        return revenueData;
    }

    // Получение данных для графика "Оборот" на странице аналитики
    public List<Map<String, Double>> getRevenueForYearChart() {
        // Получение данных о продажах за год
        List<Map<String, Double>> salesData = getSalesForYearChart();
        // Получение данных о зарплатах за год
        List<Map<String, BigDecimal>> salaryData = teacherService.getSalaryForMonthScale();

        // Создание карты для хранения общих продаж по месяцам
        Map<Integer, Double> totalSalesByMonth = new HashMap<>();
        // Заполнение карты данными о продажах за год
        for (Map<String, Double> sale : salesData) {
            int month = sale.get("month").intValue();
            double sales = sale.get("sales");
            totalSalesByMonth.put(month, sales);
        }

        // Создание карты для хранения общих зарплат по месяцам
        Map<Integer, BigDecimal> totalSalaryByMonth = new HashMap<>();
        // Заполнение карты данными о зарплатах за год
        for (Map<String, BigDecimal> salary : salaryData) {
            int month = salary.get("month").intValue();
            BigDecimal totalSalary = salary.get("totalSalary");
            totalSalaryByMonth.put(month, totalSalary);
        }

        List<Map<String, Double>> revenueData = new ArrayList<>();

        // Вычисление оборота для каждого месяца
        for (int month = 1; month <= 12; month++) {
            double sales = totalSalesByMonth.getOrDefault(month, 0.0);
            BigDecimal salary = totalSalaryByMonth.getOrDefault(month, BigDecimal.ZERO);
            double revenue = sales - salary.doubleValue();

            Map<String, Double> dataPoint = new HashMap<>();
            dataPoint.put("month", (double) month);
            dataPoint.put("revenue", revenue);
            revenueData.add(dataPoint);
        }
        return revenueData;
    }

    public List<Map<String, Object>> getIncomesForRevenueChart() {
        List<Map<String, Object>> incomesData = new ArrayList<>();

        // Получаем все заказы за текущий год
        LocalDate startDate = LocalDate.now().withDayOfYear(1); // Начало текущего года
        LocalDate endDate = startDate.plusYears(1).minusDays(1); // Конец текущего года
        List<Order> orders = orderRepository.findByOrderTimeBetween(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));

        // Группируем заказы по времени добавления в базу данных и суммируем общий доход
        Map<LocalDateTime, Double> salesByTime = new TreeMap<>(); // Используем TreeMap для сортировки по времени
        for (Order order : orders) {
            LocalDateTime orderTime = order.getOrderTime().truncatedTo(ChronoUnit.MINUTES); // Округляем до минут для точности
            double sales = order.getOrderPrice();
            salesByTime.put(orderTime, salesByTime.getOrDefault(orderTime, 0.0) + sales);
        }

        // Вычисляем накопительную сумму
        double cumulativeSales = 0.0;
        for (Map.Entry<LocalDateTime, Double> entry : salesByTime.entrySet()) {
            cumulativeSales += entry.getValue();
            Map<String, Object> dataPoint = new HashMap<>();
            LocalDateTime time = entry.getKey();
            String formattedTime = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            dataPoint.put("time", formattedTime);
            dataPoint.put("sales", String.valueOf(cumulativeSales));
            incomesData.add(dataPoint);
        }

        return incomesData;
    }
}
