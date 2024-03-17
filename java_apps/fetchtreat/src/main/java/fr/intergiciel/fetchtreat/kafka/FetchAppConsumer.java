package fr.intergiciel.fetchtreat.kafka;

import fr.intergiciel.fetchtreat.DB;
import fr.intergiciel.fetchtreat.tables.*;
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
            }
            else if(Objects.equals(command, "get_patient_by_pid") && parameter != null) {
                return getPatientByPID(parameter);
            }else if(Objects.equals(command, "get_patient_by_name") && parameter != null) {
                return getPatientByName(parameter);
            }else if(Objects.equals(command, "get_patient_stay_by_pid") && parameter != null) {
                return getPatientStayByPID(parameter);
            }else if(Objects.equals(command, "get_patient_movements_by_sid") && parameter != null) {
                //return getPatientMovementsBySID(parameter);
            }else if(Objects.equals(command, "export") && parameter != null) {
                //return exportDataToJson(parameter);
            }else {
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
        if (patients.isEmpty()) {
            System.out.println("patients is empty");
            return ("Aucun résultat trouvé dans la base de données.");
        return patients;
    }

    public Object getPatientByPID(String pid) {
        Patient patient = null;
        String query = "SELECT * FROM patient WHERE patient.patient_id = ? ";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, pid);
            resultSet = statement.executeQuery();

            if (resultSet.next()) { // Appeler la méthode next() avant de récupérer des données
                String patientId = resultSet.getString("patient_id");
                String birthName = resultSet.getString("birth_name");
                String legalName = resultSet.getString("legal_name");
                String firstName = resultSet.getString("first_name");
                String prefix = resultSet.getString("prefix");
                java.sql.Date birthDate = resultSet.getDate("birth_date");

                patient = new Patient(patientId, birthName, legalName, firstName, prefix, birthDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        if (patient == null) {
            System.out.println("patient is null");
//          TODO : Erreur à traiter à un moment
            return ("Aucun résultat trouvé dans la base de données.");
        }
        System.out.println("patient : " + patient.toString());
        return (patient);
    }


    public ArrayList<String> getPatientByName(String name) {
        ArrayList<String> patients = new ArrayList<>();

        System.out.println(connection.toString());

        String query = "SELECT * FROM patient WHERE patient.birth_name LIKE ? OR patient.legal_name LIKE ? OR patient.first_name LIKE ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, name);
            statement.setString(3, name);
            resultSet = statement.executeQuery();
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

        if (patients.isEmpty()) {
            System.out.println("patients is empty");
            return ("Aucun résultat trouvé dans la base de données.");

        return patients;
    }

    public ArrayList<String> getStaysByPatientID(String patientID) {
        ArrayList<String> stays = new ArrayList<>();
        String query = "SELECT * FROM Stay WHERE patient_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, patientID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {

                    java.sql.Date start_date= resultSet.getDate("start_date");

                    java.sql.Date end_date= resultSet.getDate("end_date");
// Assuming Stay class exists with appropriate constructor
                    Stay stay = new Stay(
                            resultSet.getString("num_sej"),
                            start_date,
                            end_date,
                            resultSet.getString("patient_id")
                    );
                    stays.add(Stay.toString());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (stays.isEmpty()) {
            System.out.println("stays is empty");
            return ("Aucun résultat trouvé dans la base de données.");
        }
        return stays;
    }

    public ArrayList<String> getMovementsByPatientID(String NumSej) {
        ArrayList<String> movements = new ArrayList<>();
        String query = "SELECT * FROM Movement WHERE num_sej = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, NumSej);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
// Assuming Movement class exists with appropriate constructor
                    Movement movement = new Movement(
                            resultSet.getInt("movement_id"),
                            resultSet.getString("service"),
                            resultSet.getString("room"),
                            resultSet.getString("bed"),
                            resultSet.getString("num_sej"),
                            resultSet.getString("patient_id")
                    );
                    movements.add(Movement.toString());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movements;
    }

    public void close() {
        kafkaConsumer.close();
    }
}