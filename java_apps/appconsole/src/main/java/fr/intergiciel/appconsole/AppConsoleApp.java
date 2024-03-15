package fr.intergiciel.appconsole;

import fr.intergiciel.appconsole.kafka.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

// Pour tester la console, rentrer la commande suivante : docker attach appconsole
// Il faut ensuite ecrire une commande puis cliquer sur entrer

public class AppConsoleApp {

    private Map<String, Method> commandMap;
    private Object targetObject;
    ConsoleProd kafkaProducer = new ConsoleProd("broker:29092", "topic2");

    public AppConsoleApp(Object targetObject) {
        this.targetObject = targetObject;
        this.commandMap = new HashMap<>();
        initializeCommands();
    }
    private void initializeCommands() {
        try {
            commandMap.put("get_all_patients", targetObject.getClass().getMethod("getAllPatients"));
            commandMap.put("get_patient_by_pid", targetObject.getClass().getMethod("getPatientByPID", String.class));
            commandMap.put("get_patient_by_name", targetObject.getClass().getMethod("getPatientByName", String.class));
            commandMap.put("get_patient_stay_by_pid", targetObject.getClass().getMethod("getPatientStayByPID", String.class));
            commandMap.put("get_patient_movements_by_sid", targetObject.getClass().getMethod("getPatientMovementsBySID", String.class));
            commandMap.put("export", targetObject.getClass().getMethod("exportDataToJson", String.class));
            commandMap.put("help", targetObject.getClass().getMethod("printHelp"));
            // Ajoutez d'autres fonctions si nécessaire
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void executeCommand(String commandLine) {
        String[] commandParts = commandLine.split("\\s+", 2);
        String command = commandParts[0];
        String parameter = commandParts.length > 1 ? commandParts[1] : null;

        Method method = commandMap.get(command);
        if (method != null) {
            try {
                if (parameter != null && method.getParameterTypes().length == 1) {
                    method.invoke(targetObject, parameter);
                } else {
                    method.invoke(targetObject);
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Commande inconnue");
        }
    }


    public static void main(String[] args) {
        YourClass yourClass = new YourClass(new ConsoleProd("broker:29092", "topic2"));
        AppConsoleApp appConsoleApp = new AppConsoleApp(yourClass);

        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            System.out.print("Entrez une commande: ");
            input = scanner.nextLine();
            if (!input.equals("exit")) {
                appConsoleApp.executeCommand(input);
            }
        } while (true);

    }
}

class YourClass {
    private final ConsoleProd kafkaProducer;

    public YourClass(ConsoleProd kafkaProducer){
        this.kafkaProducer = kafkaProducer;
    }

    // Ajoutez ici les implémentations des fonctions demandées

    public void getAllPatients() {
        kafkaProducer.sendMessage("get_all_patients");
        System.out.println("Fonction get_all_patients exécutée");
        // Implémentez la logique pour récupérer tous les patients
    }

    public void getPatientByPID(String pid) {
        kafkaProducer.sendMessage("getPatientByPID " + pid);
        System.out.println("Fonction get_patient_by_pid exécutée avec PID: " + pid);
        // Implémentez la logique pour récupérer un patient par son PID
    }

    public void getPatientByName(String name) {
        kafkaProducer.sendMessage("getPatientByName " + name);
        System.out.println("Fonction get_patient_by_name exécutée avec nom: " + name);
        // Implémentez la logique pour récupérer un patient par son nom
    }

    public void getPatientStayByPID(String pid) {
        kafkaProducer.sendMessage("getPatientStayByPID " + pid);
        System.out.println("Fonction get_patient_stay_by_pid exécutée avec PID: " + pid);
        // Implémentez la logique pour récupérer les séjours d'un patient par son PID
    }

    public void getPatientMovementsBySID(String sid) {
        kafkaProducer.sendMessage("getPatientMovementsBySID " + sid);
        System.out.println("Fonction get_patient_movements_by_sid exécutée avec SID: " + sid);
        // Implémentez la logique pour récupérer les mouvements d'un patient par son SID
    }

    public void exportDataToJson(String pid) {
        kafkaProducer.sendMessage("exportDataToJson " + pid);
        System.out.println("Fonction export exécutée avec le PID: "+ pid);
        // Implémentez la logique pour exporter les données en JSON
    }

    public void printHelp() {
        System.out.println("Liste des commandes de la console:");
        System.out.println("- get_all_patients (retourne tous les patients)");
        System.out.println("- get_patient_by_pid (retourne l’identité complète d’un patient par son identifiant PID-3)");
        System.out.println("- get_patient_by_name (retourne l’identité d’un patient par son l’un de ses noms tout ou partie du nom !)");
        System.out.println("- get_patient_stay_by_pid (retourne les séjours d’un patient par son identifiant PID-3)");
        System.out.println("- get_patient_movements_by_sid (retourne tous les mouvements d’un patients par le numéro de séjour)");
        System.out.println("- export (Exporte les données de la base de données en JSON dans un fichier)");
        System.out.println("- help (Affiche la liste des commandes et une explication comme ci-dessus)");
        // Ajoutez d'autres commandes si nécessaire
    }
}
