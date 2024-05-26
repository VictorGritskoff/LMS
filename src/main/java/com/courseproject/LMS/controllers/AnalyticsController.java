package com.courseproject.LMS.controllers;

import com.courseproject.LMS.services.ClientService;
import com.courseproject.LMS.services.OrderService;
import com.courseproject.LMS.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
public class AnalyticsController {

    private final OrderService orderService;
    private final TeacherService teacherService;
    private final ClientService clientService;
    @Autowired
    public AnalyticsController(OrderService orderService, TeacherService teacherService, ClientService clientService) {
        this.orderService = orderService;
        this.teacherService = teacherService;
        this.clientService = clientService;
    }


    @GetMapping("/analytics")
    public String getAnalytics(Model model) {
        // Получение данных для графика курсов по неделям
        List<Map<String, Integer>> countOrdersData = orderService.getCourseCountForSparkChart();
        // Получение данных для графика зарплат за год
        List<Map<String, BigDecimal>> totalSalary = teacherService.getSalaryForMonthScale();
        // Получение данных для продаж в год
        List<Map<String, Double>> totalSales = orderService.getSalesForYearChart();
        // Получение данных для оборота в год
        List<Map<String, Double>> revenue = orderService.getRevenueForYearChart();
        // Получение данных для количества клиентов в год
        List<Map<String, Double>> clientCount = clientService.getCustomersForYearChart();
        System.out.println(clientCount);

        model.addAttribute("countOrdersData", countOrdersData);
        model.addAttribute("totalSalary", totalSalary);
        model.addAttribute("totalSales", totalSales);
        model.addAttribute("revenueSpark", revenue);
        model.addAttribute("clientCount", clientCount);

        return "analytics";
    }
}