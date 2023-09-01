package com.example.dbdemo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        //Verify if the driver is loaded successfully or not, else exit the application
        boolean isDriverLoaded = DatabaseHelper.checkDriver();
        if (!isDriverLoaded) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Driver not loaded! Please try again.");
            alert.showAndWait();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}