package com.course.kafka.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long orderItemId;

    @Column
    private String itemName;
    @Column
    private int price;
    @Column
    private int quantity;


    @ManyToOne
    @JoinColumn(name = "fk_order")
    private Order order;

}
