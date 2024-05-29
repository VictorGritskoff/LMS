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
        // Получение начальной и конечной даты для текущего года
        LocalDate startDate = LocalDate.now().withDayOfYear(1); // Начало текущего года
        LocalDate endDate = startDate.plusYears(1).minusDays(1); // Конец текущего года

        List<Map<String, Double>> customersCountForYear = new ArrayList<>();

        // Получение списка клиентов за текущий год
        List<Client> customers = clientRepository.findByCreationDateBetween(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));

        // Группирование клиентов по месяцам и подсчет их количества
        Map<Integer, Long> customersByMonth = new HashMap<>();
        for (Client customer : customers) {
            int month = customer.getCreationDate().getMonthValue();
            customersByMonth.put(month, customersByMonth.getOrDefault(month, 0L) + 1);
        }

        // Формирование данных для возврата
        for (int month = 1; month <= 12; month++) {
            Map<String, Double> dataPoint = new HashMap<>();
            dataPoint.put("month", (double) month);
            dataPoint.put("client", customersByMonth.getOrDefault(month, 0L).doubleValue());
            customersCountForYear.add(dataPoint);
        }

        return customersCountForYear;
    }

    // ДОПЕРЕДЕЛАТЬ
    public double calculateExpectedCustomers(double revenue, double averageCoursePrice) {
        if (revenue < 0) {
            double loss = Math.abs(revenue);
            return loss / averageCoursePrice;
        } else {
            double adjustedRevenue = revenue * 1.1; // Увеличиваем оборот на 10%
            return adjustedRevenue / averageCoursePrice;
        }
    }
    public List<Map<String, Map<String, Double>>> getExpectedVsRealCustomers() {
        List<Map<String, Map<String, Double>>> result = new ArrayList<>();
        List<Map<String, Double>> revenueData = orderService.getRevenueForYearChart();
        List<Map<String, Double>> customersData = getCustomersForYearChart();

        double averageCoursePrice = courseService.calculateAverageCoursePrice();

        for (int i = 0; i < revenueData.size(); i++) {
            Map<String, Map<String, Double>> monthData = new HashMap<>();

            // Получаем данные по доходам и количеству клиентов за месяц
            Map<String, Double> revenueMap = revenueData.get(i);
            Map<String, Double> customerMap = customersData.get(i); // Получаем данные о клиентах для данного месяца

            double revenue = revenueMap.get("revenue");
            double currentCustomers = customerMap.get("client");
            double expectedCustomers = calculateExpectedCustomers(revenue, averageCoursePrice);
            if (currentCustomers == 0) {
                expectedCustomers *= 1.1;
            }
            // Записываем результат в формате (месяц: {ожидаемое количество клиентов: реальное количество клиентов})
            Map<String, Double> expectedVsReal = new HashMap<>();
            expectedVsReal.put("Expected", expectedCustomers);
            expectedVsReal.put("Real", currentCustomers);

            monthData.put(String.valueOf(i + 1), expectedVsReal);
            result.add(monthData);
        }

        return result;
    }
}
