package kz.pashim;


import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

import java.util.Map;

import static org.apache.kafka.clients.consumer.ConsumerConfig.ALLOW_AUTO_CREATE_TOPICS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.RETRY_BACKOFF_MS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG;

@SpringBootApplication
@Configuration
@EnableKafka
@EnableKafkaStreams
public class Main {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean(name =
            KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfigs() {
        return new KafkaStreamsConfiguration(Map.of(
                APPLICATION_ID_CONFIG, "testStreams",
                BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress,
                RETRY_BACKOFF_MS_CONFIG, 5000,
                ALLOW_AUTO_CREATE_TOPICS_CONFIG, true,
                DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName(),
                DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName()
        ));
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}