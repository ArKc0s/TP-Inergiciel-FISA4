package fr.intergiciel.fetchtreat.kafka;

import fr.intergiciel.fetchtreat.DB;
import fr.intergiciel.fetchtreat.tables.Patient;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.lang.reflect.InvocationTargetException;

public class FetchAppConsumer {

    private KafkaConsumer<String, String> kafkaConsumer;
    private String topic;
    private Connection connection;

    public FetchAppConsumer(String bootstrapServers, String topic) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("group.id", "group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        this.kafkaConsumer = new KafkaConsumer<>(props);
        this.topic = topic;

        try {
            connection = DB.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Object consumeMessages() {
        kafkaConsumer.subscribe(Collections.singletonList(topic));

        ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
        for (ConsumerRecord<String, String> record : records) {
            System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());

            String[] commandParts = record.value().split("\\s+", 2);
            String command = commandParts[0];
            String parameter = commandParts.length > 1 ? commandParts[1] : null;

            //TODO: Mettre des else-if pour chaque commande, pour les commandes a param, mettre la variable parameter en paramètre de la fonction
            if(Objects.equals(command, "get_all_patients")) {
                return getAllPatients();
            } else {
                System.out.println("Commande inconnue");
            }

        }
        return null;
    }


    //TODO: Faire les fonctions pour chaque requete, chaque fonction doit retourner un Object toStringable (une ArrayList, un String, un Patient...)

    public ArrayList<String> getAllPatients() {
        ArrayList<String> patients = new ArrayList<>();

        System.out.println(connection.toString());

        String query = "SELECT * FROM patient";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String patientId = resultSet.getString("patient_id");
                String birthName = resultSet.getString("birth_name");
                String legalName = resultSet.getString("legal_name");
                String firstName = resultSet.getString("first_name");
                String prefix = resultSet.getString("prefix");
                java.sql.Date birthDate = resultSet.getDate("birth_date");

                Patient patient = new Patient(patientId, birthName, legalName, firstName, prefix, birthDate);
                patients.add(patient.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patients;
    }

//    public Patient getPatientByPID(String pid) {
//        Patient patient = None;
//        String query = "SELECT * FROM patient WHERE patient.patient_id = ? " ;
//        try (PreparedStatement statement = connection.prepareStatement(query);
//             statement.setString(1, pid);
//             ResultSet resultSet = statement.executeQuery()){
//            if (resultSet.size() == 1) {
//                String patientId = resultSet.getString("patientId");
//                String birthName = resultSet.getString("birthName");
//                String legalName = resultSet.getString("legalName");
//                String firstName = resultSet.getString("firstName");
//                String prefix = resultSet.getString("prefix");
//                java.sql.Date birthDate = resultSet.getDate("birthDate");
//                Patient patient = new Patient(patientId, birthName, legalName, firstName, prefix, birthDate);
//            } else if (resultSet.size() == 0) {
//                return "Aucun patient trouvé.";
//            }
//
//        }catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return patient;
//    }

//    public static getPatientByName(String name) {
//        pass
//    }

    public void close() {
        kafkaConsumer.close();
    }
}