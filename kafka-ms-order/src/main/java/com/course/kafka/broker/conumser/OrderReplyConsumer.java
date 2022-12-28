package com.course.kafka.broker.conumser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderReplyConsumer {
    private static final Logger log = LoggerFactory.getLogger(OrderReplyConsumer.class);

    @KafkaListener(topics = "t-commmodity-order-reply")
    public void listen (OrderReplyConsumer message){
        log.info("reply message received : {}", message);
    }
}
