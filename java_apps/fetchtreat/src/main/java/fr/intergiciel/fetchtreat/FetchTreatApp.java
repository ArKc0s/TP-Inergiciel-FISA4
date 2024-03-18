package fr.intergiciel.fetchtreat;

import fr.intergiciel.fetchtreat.kafka.*;
import fr.intergiciel.fetchtreat.tables.*;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

public class FetchTreatApp {

    public static void main(String[] args) {

        FetchAppConsumer kafkaConsumer = new FetchAppConsumer("broker:29092", "topic2");
        FetchAppProducer kafkaProducer = new FetchAppProducer("broker:29092", "topic3");

        while (true) {
            Object result = kafkaConsumer.consumeMessages();
            if (result != null) {
                if (result instanceof String) {
                    String message = (String) result;
                    System.out.println("Message : " + message);
                }else if (result instanceof Patient) {
                    kafkaProducer.sendMessage(result.toString());
                } else if (result instanceof Stay) {
                    kafkaProducer.sendMessage(result.toString());
                } else if (result instanceof Movement) {
                    kafkaProducer.sendMessage(result.toString());
                } else {
                    kafkaProducer.sendMessage(result.toString());
                }
            }
            result = null;
        }

    }
}
