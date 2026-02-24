package com.morrisonsisland.cadvs.feb5;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * NorthWindBrowser uses NorthWindDisplay class to display names from Northwind
 * database
 *
 */
public class NorthWindBrowser extends Application {
    List<NorthWindDisplay> list = new ArrayList<>();
    // List of contact table properties

    private final GridPane gridPane = new GridPane();
    private final Label lblName = new Label("Search by Name");
    private ObservableList<NorthWindDisplay> observableNames;
    TableView<NorthWindDisplay> contactTableView = new TableView<>();

    public NorthWindBrowser() {

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("JDBC driver loaded");
        } catch (Exception err) {
            System.err.println("Error loading JDBC driver");
            err.printStackTrace(System.err);
            System.exit(0);
        }
        Connection databaseConnection = null;
        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter your SQL Username:");
            String username = reader.readLine();
            System.out.print("Enter your SQL Password:");
            String password = reader.readLine();
            System.out.print("Enter your SQL Database:");
            String database = reader.readLine();

            // String connectionUrl = "jdbc:sqlserver://sql.coccork.ie:8443;" +
            // "databaseName=" + database + ";user="
            // + username + ";password=" + password + ";";

            String connectionUrl = "jdbc:sqlserver://sql.micampus.ie:8443;databaseName=Northwind;user=teacher;password=Ema1ls;trustServerCertificate=true;encrypt=true";
            System.out.println(connectionUrl);

            databaseConnection = DriverManager.getConnection(connectionUrl);
            System.out.println("Connected to the database");

            System.out.println("Database Connected");
            String queryString = "select * from Employees where city = 'London'";
            //String queryString = "select * from Employees ";
            //	String queryString = "select * from Employees where FirstName = 'Nancy'";
            // String queryString = "select * from Employees";
            System.out.println(queryString);
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(queryString);
            ResultSet rset = preparedStatement.executeQuery();

            while (rset.next()) {
                String name = rset.getString("Lastname");

                String cname = rset.getString("FirstName");

                System.out.println(name + " " + cname);
                NorthWindDisplay p = new NorthWindDisplay();
                p.setName(rset.getString("LastName"));
                p.setCname(rset.getString("FirstName"));

                list.add(p);
                System.out.println("found one ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        lblName.setTextFill(Color.web("#0076a3"));
        observableNames = FXCollections.observableArrayList(list);
    }

    @Override
    public void start(Stage primaryStage) {
        observableNames = FXCollections.observableArrayList(list);
        TableView<NorthWindDisplay> contactsTable = new TableView<>();

        TableColumn<NorthWindDisplay, String> name = new TableColumn<>("Last Name");
        contactsTable.getColumns().add(name);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.setCellFactory(TextFieldTableCell.forTableColumn());

        TableColumn<NorthWindDisplay, String> cname = new TableColumn<>("First name");
        contactsTable.getColumns().add(cname);
        cname.setCellValueFactory(new PropertyValueFactory<>("cname"));
        cname.setCellFactory(TextFieldTableCell.forTableColumn());

        contactsTable.setItems(observableNames);
        contactsTable.setEditable(true);
        primaryStage.setTitle("Contact Names");
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 650, 400, true);

        gridPane.add(contactsTable, 0, 2);

        borderPane.setCenter(contactsTable);
        borderPane.setLeft(gridPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
