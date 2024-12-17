package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/feedback_system";
    private static final String USER = "postgres";
    private static final String PASSWORD = "132569313";


    public static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection to PostgreSQL established.");
        } catch (SQLException e) {
            System.err.println("Connection to PostgreSQL failed.");
            e.printStackTrace();
        }
        return connection;
    }
}
