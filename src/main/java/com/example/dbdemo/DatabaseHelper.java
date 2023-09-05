package com.example.dbdemo;

import org.sqlite.JDBC;

import java.sql.*;

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
        Connection connection = connect();
        if (connection != null) {
            System.out.println("Database connected successfully!");
            return true;
        }
        return false;
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
                System.out.println("Table not found!");
                //Ensure that the table exists
                return createTable(connection);
            }
        } catch (Exception e) {
            System.out.println("Couldn't find table. Reason:" + e.getMessage());
        }
        return false;
    }

    private static boolean createTable(Connection connection) {
        //1. Prepare a query to create a table
        String createTableQuery = """
                CREATE TABLE test_tbl (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name VARCHAR(100)
                );
                """;

        try {
            if (connection == null) connection = connect();
            if (connection != null) {
                //2. Create SQL statement
                Statement statement = connection.createStatement();
                //3. Execute the statement
                statement.execute(createTableQuery);
                System.out.println("Table created successfully!");
                return true;
            }
        } catch (SQLException sqlException) {
            System.out.println("Failed to create table. Reason: " + sqlException.getMessage());
        }
        return false;
    }

    public static Connection connect() {
        String DB_PREFIX = "jdbc:sqlite:";
        try {
            return DriverManager.getConnection(DB_PREFIX + DATABASE_LOCATION);
        } catch (Exception e) {
            System.out.println("Connection failed! Reason: " + e.getMessage());
        }
        return null;
    }
}
