package com.courseproject.LMS.services;

import com.courseproject.LMS.models.Order;
import com.courseproject.LMS.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
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

        // Группировать заказы по месяцу и вычислять суммарную прибыль для каждого месяца
        Map<YearMonth, Double> profitByMonth = orders.stream()
                .collect(Collectors.groupingBy(
                        order -> YearMonth.from(order.getOrderTime().toLocalDate()),
                        Collectors.summingDouble(Order::getOrderPrice)
                ));

        // Заполнить недостающие месяцы нулями и добавить значения вручную
        YearMonth currentMonth = YearMonth.from(startDate);
        YearMonth endMonth = YearMonth.from(endDate);

        while (!currentMonth.isAfter(endMonth)) {
            profitByMonth.putIfAbsent(currentMonth, 0.0);
            currentMonth = currentMonth.plusMonths(1);
        }

        // Преобразовать данные в формат, подходящий для передачи на график
        profitByMonth.forEach((month, profit) -> {
            Map<String, Object> dataPoint = new HashMap<>();
            dataPoint.put("orderTime", month.atDay(1).toString());
            dataPoint.put("profit", profit);
            profitData.add(dataPoint);
        });

        updateProfitData(profitData, "2024-01-01", 1000.0);
        updateProfitData(profitData, "2024-02-01", 1500.0);
        updateProfitData(profitData, "2024-03-01", 2000.0);
        updateProfitData(profitData, "2024-04-01", 2500.0);

        profitData.sort(Comparator.comparing(a -> LocalDate.parse(a.get("orderTime").toString())));

        return profitData;
    }

    private void updateProfitData(List<Map<String, Object>> profitData, String date, double profit) {
        for (Map<String, Object> data : profitData) {
            if (data.get("orderTime").equals(date)) {
                data.put("profit", profit);
                return;
            }
        }
        // Если дата не найдена, добавляем новую запись
        Map<String, Object> dataPoint = new HashMap<>();
        dataPoint.put("orderTime", date);
        dataPoint.put("profit", profit);
        profitData.add(dataPoint);
    }

    public List<Order> getRecentOrders() {
        return orderRepository.findTop6ByOrderByOrderTimeDesc();
    }

    // Получение данных для графика "Заказы в месяц" на странице аналитики
    public List<Map<String, Integer>> getCourseCountForSparkChart() {
        LocalDate startDate = LocalDate.now().withDayOfMonth(1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        List<Map<String, Integer>> courseCountData = new ArrayList<>();


        List<Order> orders = orderRepository.findByOrderTimeBetween(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));


        Map<Integer, Integer> courseCountByWeek = new HashMap<>();
        for (Order order : orders) {
            int weekNumber = getWeekNumber(order.getOrderTime());
            courseCountByWeek.put(weekNumber, courseCountByWeek.getOrDefault(weekNumber, 0) + 1);
        }


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
        LocalDate startDate = LocalDate.now().withDayOfYear(1); // Начало текущего года
        LocalDate currentDate = LocalDate.now(); // Текущая дата

        List<Map<String, Double>> revenueData = new ArrayList<>();

        // Получение заказов за текущий год до текущей даты
        List<Order> orders = orderRepository.findByOrderTimeBetween(startDate.atStartOfDay(), currentDate.atTime(23, 59, 59));

        // Группирование продаж по месяцам
        Map<Integer, Double> salesByMonth = new HashMap<>();
        for (Order order : orders) {
            int month = order.getOrderTime().getMonthValue();
            double sales = order.getOrderPrice();
            salesByMonth.put(month, salesByMonth.getOrDefault(month, 0.0) + sales);
        }

        // Накопительные продажи до текущего месяца
        double cumulativeSales = 0.0;
        for (int month = 1; month <= currentDate.getMonthValue(); month++) {
            cumulativeSales += salesByMonth.getOrDefault(month, 0.0);
            Map<String, Double> dataPoint = new HashMap<>();
            dataPoint.put("month", (double) month);
            dataPoint.put("sales", cumulativeSales);
            revenueData.add(dataPoint);
        }

        return revenueData;
    }

    // Получение данных для графика "Оборот" на странице аналитики
    public List<Map<String, Double>> getRevenueForYearChart() {
        List<Map<String, Double>> salesData = getSalesForYearChart();
        List<Map<String, BigDecimal>> salaryData = teacherService.getSalaryForMonthScale();

        Map<Integer, Double> totalSalesByMonth = new HashMap<>();
        for (Map<String, Double> sale : salesData) {
            int month = sale.get("month").intValue();
            double sales = sale.get("sales");
            totalSalesByMonth.put(month, sales);
        }

        Map<Integer, BigDecimal> totalSalaryByMonth = new HashMap<>();
        for (Map<String, BigDecimal> salary : salaryData) {
            int month = salary.get("month").intValue();
            BigDecimal totalSalary = salary.get("totalSalary");
            totalSalaryByMonth.put(month, totalSalary);
        }

        List<Map<String, Double>> revenueData = new ArrayList<>();

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

        LocalDate startDate = LocalDate.now().withDayOfYear(1);
        LocalDate endDate = startDate.plusYears(1).minusDays(1);
        List<Order> orders = orderRepository.findByOrderTimeBetween(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));

        Map<LocalDateTime, Double> salesByTime = new TreeMap<>();
        for (Order order : orders) {
            LocalDateTime orderTime = order.getOrderTime().truncatedTo(ChronoUnit.MINUTES);
            double sales = order.getOrderPrice();
            salesByTime.put(orderTime, salesByTime.getOrDefault(orderTime, 0.0) + sales);
        }

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
