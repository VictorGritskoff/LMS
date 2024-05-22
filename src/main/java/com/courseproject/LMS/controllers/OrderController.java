package com.courseproject.LMS.controllers;

import com.courseproject.LMS.models.Client;
import com.courseproject.LMS.models.Course;
import com.courseproject.LMS.models.Order;
import com.courseproject.LMS.services.ClientService;
import com.courseproject.LMS.services.CourseService;
import com.courseproject.LMS.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private OrderService orderService;
    @GetMapping("/orders")
    public String getOrders(Model model){
        List<Client> clientList = clientService.getClients();
        List<Course> courseList = courseService.getCourses();
        List<Order> orderList = orderService.getOrders();
        model.addAttribute("customers", clientList);
        model.addAttribute("courses", courseList);
        model.addAttribute("orders", orderList);
        return "orders";
    }
    @PostMapping("/orders/addNew")
    public String addNew(@RequestParam("client") int clientId, @RequestParam("course") int courseId, @RequestParam("orderPrice") double orderPrice){
        Client client = clientService.getClientById(clientId);
        Course course = courseService.getCourseById(courseId);

        Order order = new Order();
        order.setClient(client);
        order.setCourse(course);
        order.setOrderPrice(orderPrice);

        orderService.save(order);
        return "redirect:/orders";
    }
    @RequestMapping("/orders/findById")
    @ResponseBody
    public Optional<Order> findById(int id){
        return orderService.findById(id);
    }

    @RequestMapping(value = "/orders/update", method = RequestMethod.POST)
    public String update(@RequestParam int id, Order order){
        order.setOrderId(id);
        orderService.save(order);
        return "redirect:/orders";
    }
    @RequestMapping(value = "/orders/delete", method = RequestMethod.POST)
    public String delete(@RequestParam int id) {
        orderService.deleteById(id);
        return "redirect:/orders";
    }
}
