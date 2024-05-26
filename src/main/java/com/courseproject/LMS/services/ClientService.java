package com.courseproject.LMS.services;

import com.courseproject.LMS.models.Client;
import com.courseproject.LMS.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class ClientService {
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
}
