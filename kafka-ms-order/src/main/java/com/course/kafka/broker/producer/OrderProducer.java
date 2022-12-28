package com.course.kafka.broker.producer;

import com.course.kafka.broker.message.OrderMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrderProducer<T> {
    // we will just do some log for callback
    private static final Logger LOG = LoggerFactory.getLogger(OrderProducer.class);

    //var producer = new KafkaProducer<String, OrderMessage>;


    private KafkaProducer<String, T> kafkaProducer;

    @Autowired
    private KafkaTemplate<String, OrderMessage> kafkaTemplate;


    public ProducerRecord<String, OrderMessage> publish(OrderMessage message) {
        var producerRecord =  buildProducerRecored(message);
        kafkaTemplate.send(producerRecord);
        /*
        *** DEPRECATED METHOD, IS THE SAME OF THE VIDEO CLASS ***
    kafkaTemplate.send(producerRecord)
        .addCallback(new ListenableFutureCallback<SendResult<String, OrderMessage>>() {
            @Override
            public void onSuccess(SendResult<String, OrderMessage> result) {
                LOG.info("Order {}, item {}, published successfully", message.getOrderNumber(), message.getItemName());
                }

            @Override
            public void onFailure(Throwable ex) {
                LOG.warn("Order {}, item {}, failed to publish because {}", message.getOrderNumber(), message.getItemName());
             }
           });
         LOG.info("Jus a dummy message for order {}, item {}", message.getOrderNumber(), message.getItemName());
        }

        ***TRYING TO DO WITH THE NEW METHOD***
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
        return null;

        }

    // To add header, we need to build object with type ProducerRecord, for only kafka-reward will consumer this header
    private ProducerRecord<String, OrderMessage> buildProducerRecored(OrderMessage message) {

            // Assume for branch location that starts with "A", they will get 25% additional reward, and the other will get 15%
            var surpriseBonus = StringUtils.startsWithIgnoreCase(message.getOrderLocation(), "A") ? 25 : 15;
            var headers = new ArrayList<Header>();
            // add surpriseBonus to header and the value must be in byte array,so convert this integer to string adn get the byte array
            var surpriseBonusHeader = new RecordHeader("surpriseBonus", Integer.toString(surpriseBonus).getBytes());

            headers.add(surpriseBonusHeader);
            // partition to sent is null, so it will go according to key
            return new ProducerRecord<String, OrderMessage>("t-commodity-order", null, message.getOrderNumber(), message, headers);
    }


}
