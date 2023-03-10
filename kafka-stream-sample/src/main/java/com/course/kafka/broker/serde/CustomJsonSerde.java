package com.course.kafka.broker.serde;

import lombok.AllArgsConstructor;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
@AllArgsConstructor
public class CustomJsonSerde<T> implements Serde<T> {

    private CustomJsonDeserializer<T> customJsonDeserializer;
    private CustomJsonSerializer<T> customJsonSerializer;

    @Override
    public Serializer<T> serializer() {
            return customJsonSerializer;
    }

    @Override
    public Deserializer<T> deserializer() {
        return customJsonDeserializer;
    }
}
