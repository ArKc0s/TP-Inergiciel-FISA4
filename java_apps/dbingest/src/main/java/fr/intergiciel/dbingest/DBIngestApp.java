package fr.intergiciel.dbingest;

public class DBIngestApp {

    public static void main(String[] args) {

        System.out.println("Hello, World!");
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
