package com.course.kafka.repository.api.server;

import com.course.kafka.command.service.OrderService;
import com.course.kafka.repository.api.request.OrderRequest;
import com.course.kafka.repository.api.response.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/order")
public class OrderApi {
    @Autowired
    private OrderService orderService;

    // consumes JSON as request body, and produces JSON as response body
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> createOrder (@RequestBody OrderRequest orderRequest){
        //1. save order using service
        var orderNumber = orderService.saveOrder(orderRequest);

        //2. create response
        var orderResponse = new OrderResponse(orderNumber);
        //3. return response
        return ResponseEntity.ok().body(orderResponse);
    }

}
