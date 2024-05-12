package com.courseproject.LMS.repositories;


import com.courseproject.LMS.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
