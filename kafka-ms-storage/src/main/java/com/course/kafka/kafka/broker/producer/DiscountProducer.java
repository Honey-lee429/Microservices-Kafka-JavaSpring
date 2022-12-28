package com.course.kafka.kafka.broker.producer;

import com.course.kafka.kafka.broker.message.DiscountMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.ExecutionException;

public class DiscountProducer {

    private static final Logger LOG = LoggerFactory.getLogger(DiscountProducer.class);

    @Autowired
    private KafkaTemplate<String, DiscountMessage> kafkaTemplate;

    // method that will publish promotion message through topic t-commmodity-promotion
    // kafka template send method is asynchronous, but when we use get() afeter send() turns sunchronous.
    // But for production, it's better to use asynchronous publish with callback, otherwise you risk you publisher to be blocked.
    public void publish(DiscountMessage message){
        try {
            var sendResult = kafkaTemplate.send("t-commodity-promotion", message).get();
            LOG.info("send result success for message {}", sendResult.getProducerRecord().value());
        } catch (InterruptedException | ExecutionException e){
            LOG.error("Error publishing {}, because {}", message, e.getMessage());
        }
    }
}
