package com.course.kafka.broker.producer;

import com.course.kafka.broker.message.OrderMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {
    @Autowired
    private KafkaTemplate<String, OrderMessage> kafkaTemplate;

    public void publish(OrderMessage message){
        kafkaTemplate.send("t-commodity-order", message.getOrderNumber(), message);
    }
}
