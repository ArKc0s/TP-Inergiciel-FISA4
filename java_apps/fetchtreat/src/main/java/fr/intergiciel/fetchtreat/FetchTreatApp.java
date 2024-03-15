package fr.intergiciel.fetchtreat;

import fr.intergiciel.fetchtreat.kafka.*;

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

        while (true) {
            kafkaConsumer.consumeMessages();
//            récupère réponse de la requête
//            kafkaproducer.sendMessage(reponse)
        }
    }

}
