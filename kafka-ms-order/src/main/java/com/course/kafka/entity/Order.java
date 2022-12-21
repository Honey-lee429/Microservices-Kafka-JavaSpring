package com.course.kafka.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@ToString
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column
    private String orderNumber;
    @Column
    private String orderLocation;
    @Column
    private LocalDateTime orderDateTime;
    @Column
    private String credtCardNumber;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items;






}
