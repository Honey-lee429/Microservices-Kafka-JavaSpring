package com.course.kafka.broker.stream.promotion;

import com.course.kafka.broker.message.PromotionMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.support.ObjectNameManager;
import org.springframework.util.ObjectUtils;

@Configuration
public class PromotionUppercaseJsonStream {
    private static final Logger LOG = LoggerFactory.getLogger(PromotionUppercaseJsonStream.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    public KStream<String, String> kstreamPromotionUppercase(StreamsBuilder builder){
        var stringserde = Serdes.String();
        var sourceStream = builder.stream("t-commodity-promotion" , Consumed.with(stringserde, stringserde));
        var uppercaseStream = sourceStream.mapValues(this::uppercasePromotionCode);

        uppercaseStream.to("t-commodity-promotion-uppercase");
        sourceStream.print(Printed.<String, String>toSysOut().withLabel("Json original stream"));
        uppercaseStream.print(Printed.<String, String>toSysOut().withLabel("Json uppercase stream"));
        return sourceStream;
    }

    public String uppercasePromotionCode(String message) {
        try {
            var original = objectMapper.readValue(message, PromotionMessage.class);
            var converted = new PromotionMessage(original.getMessagePromotionCode().toUpperCase());


            return objectMapper.writeValueAsString(converted);
        } catch (Exception e) {
            return "";
        }
    }
}
