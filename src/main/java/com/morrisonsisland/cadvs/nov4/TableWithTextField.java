package com.morrisonsisland.cadvs.nov4;

import com.morrisonsisland.cadvs.oct23.Data;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;

public class TableWithTextField extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Table View with TextField");
        TableView<Data> table = new TableView<>();
        table.setEditable(true);

        TableColumn<Data, String> istColumn = new TableColumn<>("Name of Groceries");
        istColumn.setMinWidth(150);
        istColumn.setMaxWidth(300);

        istColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Data, Double> secColumn = new TableColumn<>("Table Prices");
        secColumn.setMinWidth(100);
        secColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Data, Integer> thrColumn = new TableColumn<>("Quantity");
        thrColumn.setMinWidth(100);
        thrColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        table.setItems(getData());
        table.getColumns().addAll(List.of(istColumn, secColumn, thrColumn));

        TextField addName = new TextField();
        addName.setPromptText("Name");

        TextField addPrice = new TextField();
        addPrice.setPromptText("Price");

        TextField addQuantity = new TextField();
        addQuantity.setPromptText("Quantity");

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {

            String name = addName.getText().trim();
            String price = addPrice.getText().trim();
            String quantity = addQuantity.getText().trim();

            Data newData = new Data(name, Double.parseDouble(price), Integer.parseInt(quantity));
            table.getItems().add(newData);
            addName.clear();
            addPrice.clear();
            addQuantity.clear();
        });

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {

            Data selectedItem = table.getSelectionModel().getSelectedItem();

            if (selectedItem != null) {
                table.getItems().remove(selectedItem);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Deletion");
                alert.setHeaderText("You deleted:");
                alert.setContentText(selectedItem.getName());
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Deletion");
                alert.setHeaderText("You must select an item in the table");
                alert.showAndWait();
            }
        });

        HBox hBox = new HBox();
        hBox.getChildren().addAll(addName, addPrice, addQuantity, addButton, deleteButton);
        hBox.setSpacing(10);

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(10);
        vBox.getChildren().addAll(table, hBox);

        Scene scene = new Scene(vBox, 700, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public ObservableList<Data> getData() {

        ObservableList<Data> data_list = FXCollections.observableArrayList();

        try {
            InputStream inputStream = getClass().getResourceAsStream("/groceries.csv");
            if (inputStream == null) {
                throw new FileNotFoundException();
            }
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream));
            System.out.println("Loadind data from file");
            String line;
            while ((line = bReader.readLine()) != null) {
                String[] groceries = line.split(",");
                data_list.add(new Data(groceries[0], Double.valueOf(groceries[1]), Integer.valueOf(groceries[2])));
            }
        } catch (IOException e) {
            System.out.println("File Handle Exception");
        }
        return data_list;
    }
}
