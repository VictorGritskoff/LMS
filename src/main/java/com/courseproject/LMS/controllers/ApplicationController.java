package com.courseproject.LMS.controllers;

import com.courseproject.LMS.models.Client;
import com.courseproject.LMS.models.Order;
import com.courseproject.LMS.services.ClientService;
import com.courseproject.LMS.services.CourseService;
import com.courseproject.LMS.services.OrderService;
import com.courseproject.LMS.services.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class ApplicationController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private OrderService orderService;
    private int getClientCount() {
        int clientCount = clientService.getClientCount();
        return clientCount;
    }

    private int getCourseCount() {
        int courseCount = courseService.getCourseCount();
        return courseCount;
    }

    private int getTeacherCount() {
        int teacherCount = teacherService.getTeacherCount();
        return teacherCount;
    }

    private double getProfitForCards() {
        double profit = orderService.getProfit();
        return profit;
    }
    public List<Map<String, Object>> getProfitDataForChart() {
        List<Map<String, Object>> profitData = orderService.getProfitDataForChart();
        return profitData;
    }
    public List<Order> getRecentOrders() {
        List<Order> recentOrders = orderService.getRecentOrders();
        return recentOrders;
    }
    public List<Client> getRecentClients() {
        List<Client> recentClients = clientService.getRecentClients();
        return recentClients;
    }
    @GetMapping("/login")
    public String loginPage() {
        return "signup";
    }

    @GetMapping("/home")
    public String getHome(Model model) {
        model.addAttribute("clientCount", getClientCount());
        model.addAttribute("courseCount", getCourseCount());
        model.addAttribute("teacherCount", getTeacherCount());
        model.addAttribute("profit", getProfitForCards());
        model.addAttribute("profitData", getProfitDataForChart());
        System.out.println(getProfitDataForChart());
        model.addAttribute("recentOrders", getRecentOrders());
        model.addAttribute("recentClients", getRecentClients());
        return "index";
    }
}
