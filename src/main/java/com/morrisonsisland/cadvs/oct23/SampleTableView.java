package com.morrisonsisland.cadvs.oct23;

import java.io.*;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

//Using TableView to show data in a tabular format

public class SampleTableView extends Application {

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("Table View");
		TableView<Data> table = new TableView<>();
		table.setEditable(false);
		
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
	//	table.getColumns().addAll(istColumn, secColumn, thrColumn);//may get warning due to incompatible types
		table.getColumns().addAll(java.util.List.of(istColumn, secColumn, thrColumn));

		StackPane layout = new StackPane();
		layout.getChildren().add(table);

		Scene scene = new Scene(layout, 500, 600);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public ObservableList<Data> getData() {
	//ObservableList is an interface and you can't create instance
	//of that so instead there is an implementation in the FX collections 
	//so this will create observable variables
		
		ObservableList<Data> data_list = FXCollections.observableArrayList();
		
		try {
            InputStream inputStream = getClass().getResourceAsStream("/groceries.csv");
            if (inputStream == null) {
                throw new FileNotFoundException();
            }
			BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream));
            System.out.println("Loadind data from file");
			String line;
			while((line = bReader.readLine()) != null) {
				String[] groceries = line.split(",");
				data_list.add(new Data(groceries[0], Double.valueOf(groceries[1]), Integer.valueOf(groceries[2])));
			}
			
		} catch (IOException e) {
			System.out.println("File Handle Exception");
		}
		return data_list;
	}
}