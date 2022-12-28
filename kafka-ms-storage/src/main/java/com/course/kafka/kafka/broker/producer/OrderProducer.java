package com.course.kafka.kafka.broker.producer;

import com.course.kafka.broker.message.OrderMessage;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer<T> {
    // we will just do some log for callback
    private static final Logger LOG = LoggerFactory.getLogger(OrderProducer.class);

    //var producer = new KafkaProducer<String, OrderMessage>;


    private KafkaProducer<String, T> kafkaProducer;

    @Autowired
    private KafkaTemplate<String, OrderMessage> kafkaTemplate;


    public void publish(OrderMessage message) {
        kafkaTemplate.send("",message);
        /*
        var aff = "null";
        var record = new ProducerRecord<>(String aff);
        kafkaProducer.send( record, (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            LOG.info("Order {}, item {}, published successfully", message.getOrderNumber(), message.getItemName());
        });
        */

        // Callback --> return success or failured message
        // publishing might not always smooth, broker might not be available, or network having latency
        // in such case, we can add callback to Future object to handle publishing success or failure
        //By using callback, we can log each publish result and find out, maybe 99,95% message is published,
        // while the other is error.


    }


}
