package com.course.kafka.command.action;

import com.course.kafka.api.request.DiscountRequest;
import com.course.kafka.api.request.PromotionRequest;
import com.course.kafka.broker.message.PromotionMessage;
import com.course.kafka.broker.producer.DiscountProducer;
import com.course.kafka.broker.producer.PromotionProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiscountAction {
    @Autowired
    private DiscountProducer producer;

    public void publishToKafka(DiscountRequest request) {
        var message = new PromotionMessage(request.getRequestDiscountCode());
        producer.publish(message);

    }
}
