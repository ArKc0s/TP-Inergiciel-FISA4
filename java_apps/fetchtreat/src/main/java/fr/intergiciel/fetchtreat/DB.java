package fr.intergiciel.fetchtreat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    public static Connection connect() throws SQLException {

        try {
            // Get database credentials from DatabaseConfig class
            String url = "jdbc:postgresql://mcdb:5432/mirthdb";
            String user = "mirthdb";
            String password = "mirthdb";

            // Open a connection
            return DriverManager.getConnection(url, user, password);

        } catch (SQLException  e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}