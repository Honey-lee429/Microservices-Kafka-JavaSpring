package com.course.kafka.broker.serde;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class CustomJsonDeserializer<T> implements Deserializer<T> {

    private ObjectMapper objectMapper = new ObjectMapper();
    //to read value, object mapper need to know the class for deserialization target
    private Class<T> deserializedClass;

    public CustomJsonDeserializer(Class<T> deserializedClass){
        this.deserializedClass = deserializedClass;
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data,deserializedClass);
        } catch (IOException e) {
            throw new SerializationException(e.getMessage());
        }
    }
}
