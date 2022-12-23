package com.course.kafka.api.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private String orderLocation;
    private String creditCardNumber;
    private List<OrderItemRequest> itemRequests;
}
