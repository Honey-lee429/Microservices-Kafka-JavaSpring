package com.course.kafka.broker.stream.promotion;

import com.course.kafka.broker.message.PromotionMessage;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
public class PromotionUppercaseSpringJsonStream {

    @Bean
    public KStream<String, PromotionMessage> kStreamPromotionUppercase(StreamsBuilder builder) {
        // The input for topology is from promotion topic, and we will consume it using string serde as key
        var stringSerde = Serdes.String();
        // we will use JsonSerde as value serde
        var jsonSerde = new JsonSerde<>(PromotionMessage.class);

        var sourceStream = builder.stream("t-commodity-promotion", Consumed.with(stringSerde, jsonSerde));

        //Transform the data using method reference to uppercasePromotionCode
        var uppercaseStream = sourceStream.mapValues(this::uppercasePromotionCode);
        //send the uppercase stream to output topic. This time, we will tell kafka stream that the key is using string serde, and value using json serde  of PromotionMessage
        uppercaseStream.to("t-commodity-promotion-uppercase", Produced.with(stringSerde, jsonSerde));

        // put some debuggin using print method for each stream and return the method
        sourceStream.print(Printed.<String, PromotionMessage> toSysOut().withLabel("json serede original stream"));
        uppercaseStream.print(Printed.<String, PromotionMessage> toSysOut().withLabel("json serede uppercase stream"));

        return sourceStream;
    }

    // method to map the original promotion message object into a new promotion message
    private PromotionMessage uppercasePromotionCode(PromotionMessage object) {
        //return modified PromotionMessage object
        return new PromotionMessage(object.getMessagePromotionCode().toUpperCase());
    }
}
