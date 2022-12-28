package com.course.kafka.kafka.broker.consumer;

import com.course.kafka.kafka.broker.message.OrderMessage;
import com.course.kafka.kafka.broker.message.OrderReplyMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class OrderReplyConsumer {

    //to configure producer in this api we will configure in application.yml
    private static final Logger LOG = LoggerFactory.getLogger(OrderReplyConsumer.class);

    @KafkaListener(topics = "t-commodity-order")
    @SendTo("t-commodity-order-reply")
    public OrderReplyMessage listen(ConsumerRecord<String, OrderMessage> consumerRecord) {
        var headers = consumerRecord.headers();
        var orderMessageBody = consumerRecord.value();

        LOG.info("processing order {}, item {}, credit card numer {}", orderMessageBody.getOrderLocation(),
                orderMessageBody.getItemName(), orderMessageBody.getCreditCardNumber());

        LOG.info("headers : ");
        headers.forEach(h -> LOG.info(" key: {} , value: {}", h.key(), new String(h.value())));

        // If no header found, handle null possibility
        var headerValue = ObjectUtils.isEmpty(headers.lastHeader("surpriseBonus").value()) ? "0"
                :new String(headers.lastHeader("surpriseBonus").value());

        var bonusPercentage = Integer.parseInt(headerValue);
        var bonusAmount = (bonusPercentage / 100d) * orderMessageBody.getPrice() * orderMessageBody.getQuantity();

        LOG.info("bonus amount is {}", bonusAmount);

        var replyMessage = new OrderReplyMessage();
        replyMessage.setReplyMessage("order " + orderMessageBody.getOrderNumber() + ", item" + orderMessageBody.getItemName() + " processed");
        return replyMessage;
    }
}
