package com.courseproject.LMS.services;

import com.courseproject.LMS.models.Client;
import com.courseproject.LMS.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ClientService {
    private final CourseService courseService;
    private final OrderService orderService;
    @Autowired
    public ClientService(CourseService courseService, OrderService orderService) {
        this.courseService = courseService;
        this.orderService = orderService;
    }
    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getClients(){
        return clientRepository.findAll();
    }

    public void save(Client client){
        clientRepository.save(client);
    }
    public Optional<Client> findById(int id){
        return clientRepository.findById(id);
    }

    public void deleteById(int id) {
        clientRepository.deleteById(id);
    }

    public Client getClientById(int clientId) {
        return clientRepository.getById(clientId);
    }
    public int getClientCount() {
        return clientRepository.findAll().size();
    }
    public List<Client> getRecentClients() {
        return clientRepository.findTop6ByOrderByCreationDateDesc();
    }
    public List<Map<String, Double>> getCustomersForYearChart() {
        LocalDate startDate = LocalDate.now().withDayOfYear(1); // Начало текущего года
        LocalDate currentDate = LocalDate.now(); // Текущая дата

        List<Map<String, Double>> customersCountForYear = new ArrayList<>();

        List<Client> customers = clientRepository.findByCreationDateBetween(startDate.atStartOfDay(), currentDate.atTime(23, 59, 59));

        Map<Integer, Long> customersByMonth = new HashMap<>();
        for (Client customer : customers) {
            int month = customer.getCreationDate().getMonthValue();
            customersByMonth.put(month, customersByMonth.getOrDefault(month, 0L) + 1);
        }

        long cumulativeCustomers = 0L;
        for (int month = 1; month <= currentDate.getMonthValue(); month++) {
            cumulativeCustomers += customersByMonth.getOrDefault(month, 0L);
            Map<String, Double> dataPoint = new HashMap<>();
            dataPoint.put("month", (double) month);
            dataPoint.put("client", (double) cumulativeCustomers);
            customersCountForYear.add(dataPoint);
        }

        return customersCountForYear;
    }

    public double calculateExpectedCustomers(double revenue, double averageCoursePrice) {
        if (revenue < 0) {
            double loss = Math.abs(revenue);
            return loss / averageCoursePrice;
        } else {
            double adjustedRevenue = revenue * 1.1; // Увеличиваем оборот на 10%
            return adjustedRevenue / averageCoursePrice;
        }
    }
    public List<Map<String, Object>> getExpectedVsRealCustomers() {
        List<Map<String, Object>> result = new ArrayList<>();
        List<Map<String, Double>> revenueData = orderService.getRevenueForYearChart();
        List<Map<String, Double>> customersData = getCustomersForYearChart();

        double averageCoursePrice = courseService.calculateAverageCoursePrice();

        int currentMonth = LocalDate.now().getMonthValue();

        for (int i = 0; i < currentMonth; i++) {
            Map<String, Double> revenueMap = revenueData.get(i);
            Map<String, Double> customerMap = customersData.get(i); // Получаем данные о клиентах для данного месяца

            double revenue = revenueMap.get("revenue");
            double currentCustomers = customerMap.get("client");
            double expectedCustomers = calculateExpectedCustomers(revenue, averageCoursePrice);

            // Формируем данные в нужном формате для графика
            Map<String, Object> monthData = new HashMap<>();
            monthData.put("x", String.valueOf(i + 1));
            monthData.put("y", currentCustomers);

            Map<String, Object> goalData = new HashMap<>();
            goalData.put("name", "Expected");
            goalData.put("value", expectedCustomers);
            goalData.put("strokeHeight", 5);
            goalData.put("strokeColor", "#775DD0");

            List<Map<String, Object>> goalsList = new ArrayList<>();
            goalsList.add(goalData);

            monthData.put("goals", goalsList);

            result.add(monthData);
        }

        return result;
    }
}
