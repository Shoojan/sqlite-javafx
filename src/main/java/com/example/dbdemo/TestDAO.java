package com.example.dbdemo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Sujan Maharjan | Sept 5, 2023
 * <p>
 * DAO: Data Access Object
 * Database baata data grab garni, ani object maa bind garni
 * </p>
 */
public class TestDAO {
    //CREATE | Insert
    private static String insertQuery = "INSERT INTO test_tbl (name) VALUES (%s);";

    //READ
    private static String readAllQuery = "SELECT * FROM test_tbl";
    private static String readOneQuery = "SELECT * FROM test_tbl WHERE id=%d";

    //UPDATE
    private static String updateQuery = "UPDATE test_tbl SET name=%s WHERE id=%d";

    //DELETE
    private String deleteQuery = "DELETE FROM test_tbl WHERE id=%d;";

    public static int addData(String name) {
        try {
            //1. Create connection
            Connection connection = DatabaseHelper.connect();
            if (connection == null) {
                System.out.println("Failed to connect!");
                return -1;
            }
            //2. Prepare the statement
            String query = String.format(insertQuery, name);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            //4. Execute the statement
            int affectedRows = preparedStatement.executeUpdate();

            //5. Get the primary key id
            if (affectedRows > 0) {
                System.out.println("Data added successfully!");
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    return id;
                }
            }
        } catch (SQLException sqlException) {
            System.out.println("Failed to add data. Reason: " + sqlException.getMessage());
        }
        return -1;
    }
}
