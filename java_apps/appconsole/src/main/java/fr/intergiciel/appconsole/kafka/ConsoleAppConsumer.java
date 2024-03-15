package fr.intergiciel.fetchtreat.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class ConsoleAppConsumer {
    private final KafkaConsumer<String, String> kafkaConsumer;
    private final String topic;

    public ConsoleAppConsumer(String bootstrapServers, String topic) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("group.id", "group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        this.kafkaConsumer = new KafkaConsumer<>(props);
        this.topic = topic;
    }

    public String consumeMessages() {
        kafkaConsumer.subscribe(Collections.singletonList(topic));

            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                return record.value();
            }
//        kafkaConsumer.commitSync();
        return null;
    }

    public void close() {
        kafkaConsumer.close();
    }
}
