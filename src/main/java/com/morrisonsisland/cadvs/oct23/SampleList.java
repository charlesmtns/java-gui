package com.morrisonsisland.cadvs.oct23;

import java.io.*;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SampleList extends Application {

    ListView<String> listview;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Course List View");

        listview = new ListView<>();
        Button b1 = new Button("Submit");

        ArrayList<String> courseText = new ArrayList<String>();
        try {
            InputStream inputStream = getClass().getResourceAsStream("/courses.txt");

            if (inputStream == null) {
                throw new FileNotFoundException();
            }
            BufferedReader textReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = textReader.readLine()) != null) {
                courseText.add(line);
            }

        } catch (IOException e ) {
            System.out.println("File Handle Exception");
        }

        listview.getItems().addAll(FXCollections.observableArrayList(courseText));
        listview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        b1.setOnAction(e -> listSelect());

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(30, 30, 30, 30));
        vbox.getChildren().addAll(listview, b1);

        Scene scene = new Scene(vbox, 300, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void listSelect() {
        ObservableList<String> selectedItems = listview.getSelectionModel().getSelectedItems();

        if (selectedItems.isEmpty()) {
            // If nothing is selected, show a warning
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select at least one item.");
            alert.showAndWait();//shows the alert and pauses the current method until the user responds.

        } else {
            // Join all selected items into a single string
            String selected = String.join(", ", selectedItems);

            // Create and show an information alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Selection");
            alert.setHeaderText("You selected:");
            alert.setContentText(selected);
            alert.showAndWait();
        }
    }
}