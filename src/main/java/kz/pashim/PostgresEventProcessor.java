package kz.pashim;

import kz.pashim.serde.UserSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostgresEventProcessor {

    private static final Serde<String> STRING_SERDE = Serdes.String();

    @Autowired
    void buildPipeline(StreamsBuilder streamsBuilder) {
        streamsBuilder
                .stream("postgres-test", Consumed.with(STRING_SERDE, UserSerde.userSerde()))
                .filter((k,v) -> v != null && v.getFullName() != null)
                .mapValues(UserSerde::capitalizeName)
                .groupByKey()
                .reduce((aggValue, newValue) -> {
                    newValue.setKeyCount(aggValue.getKeyCount() == null ? 1 : aggValue.getKeyCount() + 1);
                    return newValue;
                }, Materialized.<String, UserSerde, KeyValueStore<Bytes, byte[]>>as("count-u-names").withValueSerde(UserSerde.userSerde()))
                .toStream()
                .to("postgres-test-processed-3", Produced.valueSerde(UserSerde.userSerde()));
    }
}
