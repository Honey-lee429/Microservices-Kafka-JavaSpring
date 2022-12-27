package com.course.kafka.broker.consumer;

import com.course.kafka.broker.message.OrderMessage;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer<T> {
   private static final Logger LOG = LoggerFactory.getLogger(OrderConsumer.class);

   // method to processing incoming message from kafka
   @KafkaListener(topics = "t-commodity-order")
    public void listen(OrderMessage message){
       // simulate processing
       var tottalItemAmount = message.getPrice() * message.getQuantity();

       LOG.info("Processing order {}, tem {}, credit card number {}. Total amount for this item is {}",
               message.getOrderNumber(), message.getCreditCardNumber(), tottalItemAmount);
   }

}
