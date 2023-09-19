package kz.pashim.serde;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.lang.reflect.Type;
import java.util.Map;

public class JsonSerializer<T> implements Serializer<T> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> props, boolean isKey) {
        // nothing to do
    }

    @Override
    public byte[] serialize(String topic, T data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            System.err.println("object serialization failed");
            return null;
        }
    }

    @Override
    public byte[] serialize(String topic, Headers headers, T data) {
        return Serializer.super.serialize(topic, headers, data);
    }

    @Override
    public void close() {
        // nothing to do
    }
}