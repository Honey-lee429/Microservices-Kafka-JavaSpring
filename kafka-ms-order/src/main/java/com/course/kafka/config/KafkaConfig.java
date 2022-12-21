package com.course.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    //To create a topic, we need to write da method that returns NewTopic and mark the method as Bean
    @Bean
    public NewTopic topicOrder(){
        return TopicBuilder.name("t-commodity-order").partitions(2).replicas(1).build();
    }
}
