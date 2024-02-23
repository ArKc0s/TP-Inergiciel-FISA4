package fr.intergiciel.appconsole;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AppConsoleApp {

    private Map<String, Method> commandMap;
    private Object targetObject;

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
            commandMap.put("export", targetObject.getClass().getMethod("exportDataToJson"));
            commandMap.put("help", targetObject.getClass().getMethod("printHelp"));
            // Ajoutez d'autres fonctions si nécessaire
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public void executeCommand(String command) {
        Method method = commandMap.get(command);
        if (method != null) {
            try {
                method.invoke(targetObject);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Commande inconnue");
        }
    }

    public static void main(String[] args) {
        YourClass yourClass = new YourClass();
        AppConsoleApp appConsoleApp = new AppConsoleApp(yourClass);

        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            System.out.print("Entrez une commande (ou 'exit' pour quitter): ");
            input = scanner.nextLine();
            if (!input.equals("exit")) {
                appConsoleApp.executeCommand(input);
            }
        } while (!input.equals("exit"));
    }
}

class YourClass {

    // Ajoutez ici les implémentations des fonctions demandées

    public void getAllPatients() {
        System.out.println("Fonction get_all_patients exécutée");
        // Implémentez la logique pour récupérer tous les patients
    }

    public void getPatientByPID(String pid) {
        System.out.println("Fonction get_patient_by_pid exécutée avec PID: " + pid);
        // Implémentez la logique pour récupérer un patient par son PID
    }

    public void getPatientByName(String name) {
        System.out.println("Fonction get_patient_by_name exécutée avec nom: " + name);
        // Implémentez la logique pour récupérer un patient par son nom
    }

    public void getPatientStayByPID(String pid) {
        System.out.println("Fonction get_patient_stay_by_pid exécutée avec PID: " + pid);
        // Implémentez la logique pour récupérer les séjours d'un patient par son PID
    }

    public void getPatientMovementsBySID(String sid) {
        System.out.println("Fonction get_patient_movements_by_sid exécutée avec SID: " + sid);
        // Implémentez la logique pour récupérer les mouvements d'un patient par son SID
    }

    public void exportDataToJson() {
        System.out.println("Fonction export exécutée");
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
