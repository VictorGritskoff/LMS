package com.courseproject.LMS.services;

import com.courseproject.LMS.models.Order;
import com.courseproject.LMS.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
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
    public List<Map<String, Object>> getProfitDataForChart() {
        LocalDate startDate = LocalDate.now().withDayOfYear(1); // Начало текущего года
        LocalDate endDate = LocalDate.now().withDayOfYear(365); // Конец текущего года

        List<Map<String, Object>> profitData = new ArrayList<>();

        // Получить заказы в заданном временном промежутке
        List<Order> orders = orderRepository.findByOrderTimeBetween(
                startDate.atStartOfDay(), endDate.atTime(23, 59, 59));

        // Группировать заказы по дате и вычислять суммарную прибыль для каждой даты
        Map<LocalDate, Double> profitByDate = new HashMap<>();
        for (Order order : orders) {
            LocalDate date = order.getOrderTime().toLocalDate();
            double profit = profitByDate.getOrDefault(date, 0.0);
            profit += order.getOrderPrice();
            profitByDate.put(date, profit);
        }

        // Преобразовать данные в формат, подходящий для передачи на график
        for (LocalDate date : profitByDate.keySet()) {
            Map<String, Object> dataPoint = new HashMap<>();
            dataPoint.put("orderTime", date); // Дата
            dataPoint.put("profit", profitByDate.get(date)); // Прибыль
            profitData.add(dataPoint);
        }
        return profitData;
    }
    public List<Order> getRecentOrders() {
        return orderRepository.findTop6ByOrderByOrderTimeDesc();
    }
}
