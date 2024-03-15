package fr.intergiciel.appconsole.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class ConsoleProd {
    private final KafkaProducer<String, String> kafkaProducer;
    private final String topic;

    public ConsoleProd(String bootstrapServers, String topic) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        this.kafkaProducer = new KafkaProducer<>(props);
        this.topic = topic;
    }

    public void sendMessage(String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
        kafkaProducer.send(record, (metadata, exception) -> {
            if (exception == null) {
                System.out.printf("Message envoyé au topic %s, partition %d, offset %d%n", metadata.topic(), metadata.partition(), metadata.offset());
            } else {
                exception.printStackTrace();
            }
        });
    }

    public void close() {
        kafkaProducer.close();
    }
}
