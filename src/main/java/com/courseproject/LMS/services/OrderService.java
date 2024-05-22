package com.courseproject.LMS.services;

import com.courseproject.LMS.models.Order;
import com.courseproject.LMS.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}
