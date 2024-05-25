package com.courseproject.LMS.repositories;


import com.courseproject.LMS.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByOrderTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
    List<Order> findTop6ByOrderByOrderTimeDesc();
}
