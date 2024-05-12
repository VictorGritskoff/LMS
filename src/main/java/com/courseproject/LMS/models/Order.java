package com.courseproject.LMS.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "order_price")
    private double orderPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;
}