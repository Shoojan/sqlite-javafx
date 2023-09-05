package com.example.dbdemo;

import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseHelper {

    private static final String DATABASE_LOCATION
            = DatabaseHelper.class
            .getResource("/com/example/dbdemo/database/test_db.db")
            .toExternalForm();

    public static boolean isDatabaseReady() {
        boolean isDriverReady = checkDriver();
        if (!isDriverReady) return false;

        boolean isConnectionReady = checkConnection();
        if (!isConnectionReady) return false;

        boolean isTableReady = checkTables();
        if (!isTableReady) return false;

        return true;

//        return checkDriver() && checkConnection() && checkTables();
    }

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

    private static boolean checkConnection() {
        return connect() != null;
    }

    private static boolean checkTables() {
        String requiredTable = "test_tbl";
        String checkTableQuery = "SELECT DISTINCT tbl_name FROM sqlite_master";

        try {
            //Establish database connection
            Connection connection = connect();
            if (connection != null) {
                //Prepare the statement
                PreparedStatement preparedStatement = connection.prepareStatement(checkTableQuery);
                //Execute it
                ResultSet resultSet = preparedStatement.executeQuery();
                //Grab it
                while (resultSet.next()) {
                    String tableName = resultSet.getString("tbl_name");
                    if (tableName.equalsIgnoreCase(requiredTable)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Couldn't find table. Reason:" + e.getMessage());
        }
        return false;
    }

    public static Connection connect() {
        String DB_PREFIX = "jdbc:sqlite:";
        try {
            Connection connection = DriverManager.getConnection(DB_PREFIX + DATABASE_LOCATION);
            System.out.println("Database connected successfully!");
            return connection;
        } catch (Exception e) {
            System.out.println("Connection failed! Reason: " + e.getMessage());
        }
        return null;
    }
}
