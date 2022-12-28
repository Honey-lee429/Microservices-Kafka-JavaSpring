package com.course.kafka.kafka.broker.consumer;

import com.course.kafka.kafka.broker.message.OrderMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(OrderConsumer.class);

    @KafkaListener(topics = "t-commodity-order")
    public void listen(ConsumerRecord<String, OrderMessage> consumerRecord) {
        var headers = consumerRecord.headers();
        var orderMessageBody = consumerRecord.value();

        LOG.info("processing order {}, item {}, credit card numer {}", orderMessageBody.getOrderLocation(),
                orderMessageBody.getItemName(), orderMessageBody.getCreditCardNumber());

        LOG.info("headers : ");
        headers.forEach(h -> LOG.info(" key: {} , value: {}", h.key(), new String(h.value())));

        var bonusPercentage = Integer.parseInt(new String(headers.lastHeader("surpriseBonus").value()));
        var bonusAmount = (bonusPercentage / 100d) * orderMessageBody.getPrice() * orderMessageBody.getQuantity();

        LOG.info("bonus amount is {}", bonusAmount);
    }
}
