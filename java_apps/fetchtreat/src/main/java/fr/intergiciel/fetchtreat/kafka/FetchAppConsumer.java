package fr.intergiciel.fetchtreat.kafka;

import fr.intergiciel.fetchtreat.DB;
import fr.intergiciel.fetchtreat.tables.Patient;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.InvocationTargetException;

public class FetchAppConsumer {

    private KafkaConsumer<String, String> kafkaConsumer;
    private String topic;
    private Map<String, Method> commandMap;
    private Connection connection;

    public FetchAppConsumer(String bootstrapServers, String topic) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("group.id", "group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        this.kafkaConsumer = new KafkaConsumer<>(props);
        this.topic = topic;
        this.commandMap = new HashMap<>();

        getCommands();

        try {
            connection = DB.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> consumeMessages() {
        kafkaConsumer.subscribe(Collections.singletonList(topic));

        ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
        for (ConsumerRecord<String, String> record : records) {
            System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            return transform_commande(record.value());
        }
        return null;
    }


    private void getCommands() {
        try {
              commandMap.put("get_all_patients", this.getClass().getMethod("getAllPatients"));
//            commandMap.put("get_patient_by_pid", targetObject.getClass().getMethod("getPatientByPID", String.class));
//            commandMap.put("get_patient_by_name", targetObject.getClass().getMethod("getPatientByName", String.class));
//            commandMap.put("get_patient_stay_by_pid", targetObject.getClass().getMethod("getPatientStayByPID", String.class));
//            commandMap.put("get_patient_movements_by_sid", targetObject.getClass().getMethod("getPatientMovementsBySID", String.class));
//            commandMap.put("export", targetObject.getClass().getMethod("exportDataToJson", String.class));
            // Ajoutez d'autres fonctions si nécessaire
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<String> transform_commande(String commandLine) {
//    Exemple de commande avec paramètre :
//        get_patient_by_pid + 12
        String[] commandParts = commandLine.split("\\s+", 2);
        String command = commandParts[0];
        String parameter = commandParts.length > 1 ? commandParts[1] : null;

        Method method = commandMap.get(command);
        if (method != null) {

            ArrayList<String> result;

            try {
                if (parameter != null && method.getParameterTypes().length == 1) {
                    result = (ArrayList<String>) method.invoke(this, parameter);
                } else {
                    result = (ArrayList<String>) method.invoke(this);
                }

                return result;

            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Commande inconnue");
        }

        return null;
    }

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
        System.out.println(patients);
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