package com.example.dbdemo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sujan Maharjan | Sept 5, 2023
 * <p>
 * DAO: Data Access Object
 * Database baata data grab garni, ani object maa bind garni
 * </p>
 */
public class TestDAO {
    //CREATE | Insert
    private static String insertQuery = "INSERT INTO test_tbl(name) VALUES ('%s');";

    //READ
    private static String readAllQuery = "SELECT * FROM test_tbl";
    private static String readOneQuery = "SELECT * FROM test_tbl WHERE id = ?";

    //UPDATE
    private static String updateQuery = "UPDATE test_tbl SET name = ? WHERE id= ?";

    //DELETE
    private static String deleteQuery = "DELETE FROM test_tbl WHERE id = ?;";

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

    public static List<TestModel> fetchAllTestData() {
        List<TestModel> allTestData = new ArrayList<>();

        try {
            //        1. Create Connection
            Connection connection = DatabaseHelper.connect();
            if (connection != null) {
                //        2. Prepare the Statement
                PreparedStatement preparedStatement = connection.prepareStatement(readAllQuery);

                //        3. Execute the query
                ResultSet resultSet = preparedStatement.executeQuery();

                //        4. Grab the results
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    TestModel testData = new TestModel(id, name);
                    allTestData.add(testData);
                }
            }
        } catch (SQLException sqlException) {
            System.out.println("Could not fetch data. Reason: " + sqlException.getMessage());
        }

        return allTestData;
    }

    public static TestModel fetchSingleTestData(int id) {
        try {
            //        1. Create Connection
            Connection connection = DatabaseHelper.connect();
            if (connection != null) {
                //        2. Prepare the Statement
                PreparedStatement preparedStatement = connection.prepareStatement(readOneQuery);
                preparedStatement.setInt(1, id);

                //        3. Execute the query
                ResultSet resultSet = preparedStatement.executeQuery();

                //        4. Grab the results
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    return new TestModel(id, name);
                }
            }
        } catch (SQLException sqlException) {
            System.out.println("Could not fetch single data. Reason: " + sqlException.getMessage());
        }

        return null;
    }

    public static boolean updateTestData(TestModel testDataToUpdate) {
        try {
            //        1. Create Connection
            Connection connection = DatabaseHelper.connect();
            if (connection != null) {
                //        2. Prepare the Statement
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setString(1, testDataToUpdate.getName());
                preparedStatement.setInt(1, testDataToUpdate.getId());

                //        3. Execute the query
                int affectedRows = preparedStatement.executeUpdate();

                //        4. check the results
                if (affectedRows > 0) {
                    return true;
                }
                //return affectedRows > 0;
            }
        } catch (SQLException sqlException) {
            System.out.println("Could not update data. Reason: " + sqlException.getMessage());
        }

        return false;
    }

    public static boolean deleteTestData(int id) {
        try {
            //        1. Create Connection
            Connection connection = DatabaseHelper.connect();
            if (connection != null) {
                //        2. Prepare the Statement
                PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
                preparedStatement.setInt(1, id);

                //        3. Execute the query
                int affectedRows = preparedStatement.executeUpdate();

                //        4. check the results
                return affectedRows > 0;
            }
        } catch (SQLException sqlException) {
            System.out.println("Could not update data. Reason: " + sqlException.getMessage());
        }
        return false;
    }

}
