package com.example.dbdemo;

import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseHelper {

    private static final String DATABASE_LOCATION
            = DatabaseHelper.class
            .getResource("/com/example/dbdemo/database/test_db.db")
            .toExternalForm();

    public static boolean checkDriver() {
        try {
            Class.forName("org.sqlite.JDBC");
            DriverManager.registerDriver(new JDBC());
            System.out.println("Driver registered successfully!");
            return true;
        } catch (Exception e) {
            System.out.println("Driver not loaded! Reason: " + e.getMessage());
            return false;
        }
    }

    public static Connection connect() {
        String DB_PREFIX = "jdbc:sqlite:";
        try {
            Connection connection = DriverManager.getConnection(DB_PREFIX + DATABASE_LOCATION);
            System.out.println("Database connected successfully!");
            return connection;
        } catch (Exception e) {
            System.out.println("Connection failed! Reason: " + e.getMessage());
            return null;
        }
    }
}
