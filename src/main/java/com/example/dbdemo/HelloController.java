package com.example.dbdemo;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class HelloController {
    public TableView<TestModel> testTableView;
    public TableColumn<TestModel, Integer> idColumn;
    public TableColumn<TestModel, String> nameColumn;

    @FXML
    private TextField nameTextField;

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    }


    public void onAddBtnClicked(ActionEvent actionEvent) {
        if (!nameTextField.getText().isBlank()) {
            String name = nameTextField.getText();
            int id = TestDAO.addData(name);
        }
    }

    public void onViewBtnClicked(ActionEvent actionEvent) {
        List<TestModel> testDataList = TestDAO.fetchAllTestData();
        testTableView.setItems(FXCollections.observableList(testDataList));

    }
}