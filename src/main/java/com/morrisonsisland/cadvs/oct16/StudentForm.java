package com.morrisonsisland.cadvs.oct16;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class StudentForm extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Student Details");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.BASELINE_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 600, 600);
        stage.setScene(scene);

        Text sceneTitle = new Text("Information Request");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label nameL = new Label("Name:");
        grid.add(nameL, 0, 1);

        TextField nameT = new TextField();
        grid.add(nameT, 1, 1);

        Label countyL = new Label("County:");
        grid.add(countyL, 0, 2);

        ComboBox<String> countyList = new ComboBox<String>();
        countyList.getItems().addAll(getCounties());
        countyList.setPromptText("Select Counties");
        countyList.setVisibleRowCount(6);
        grid.add(countyList, 1, 2);

        Label hobbieL = new Label("Hobbies:");
        grid.add(hobbieL, 0, 3);

        CheckBox box1 = new CheckBox("Reading");
        CheckBox box2 = new CheckBox("Sports");
        CheckBox box3 = new CheckBox("Music");
        CheckBox box4 = new CheckBox("Gaming");

        VBox vBoxHobbies = new VBox(10);
        vBoxHobbies.setPadding(new Insets(10, 10, 10, 0));
        vBoxHobbies.getChildren().addAll(box1, box2, box3, box4);
        grid.add(vBoxHobbies, 1, 3);

        Label howComeL = new Label("How do you come to college?");
        grid.add(howComeL, 0, 4);


        ToggleGroup groupHowCome = new ToggleGroup();
        RadioButton busR = new RadioButton("Bus");
        busR.setUserData("Bus");
        busR.setToggleGroup(groupHowCome);
        busR.setSelected(true);
        RadioButton bikeR = new RadioButton("Bike");
        bikeR.setUserData("Bike");
        bikeR.setToggleGroup(groupHowCome);
        RadioButton carR = new RadioButton("Car");
        carR.setUserData("Car");
        carR.setToggleGroup(groupHowCome);

        HBox hBoxHowCome = new HBox(10);
        hBoxHowCome.setPadding(new Insets(10, 10, 10, 0));
        hBoxHowCome.getChildren().addAll(busR, bikeR, carR);
        grid.add(hBoxHowCome, 1, 4);

        Button submit = new Button("Submit");
        HBox hbSubmit = new HBox(10);
        hbSubmit.setAlignment(Pos.BOTTOM_LEFT);
        hbSubmit.getChildren().add(submit);
        grid.add(hbSubmit, 0, 5);

        Text actionTarget = new Text();
        grid.add(actionTarget, 0, 6);


        submit.setOnAction(e -> {

            StringBuilder textTarget = new StringBuilder();
            textTarget.append("Name: ").append(nameT.getText()).append("\n");
            textTarget.append("County: ").append(countyList.getValue()).append("\n");
            String hobbiesSelected = vBoxHobbies.getChildren().filtered(n -> {
                if (n instanceof CheckBox) {
                    CheckBox chk = (CheckBox) n;
                    if (chk.isSelected()) {
                        return true;
                    }
                }
                return false;
            }).stream().map(n -> {
                CheckBox chk = (CheckBox) n;
                return chk.getText();
            }).collect(Collectors.joining(", "));
            textTarget.append("Hobbies: ").append(hobbiesSelected).append("\n");
            textTarget.append("How do you come to college? ").append(groupHowCome.getSelectedToggle().getUserData().toString());


            actionTarget.setText(textTarget.toString());
        });

        stage.show();
    }

    private List<String> getCounties() {
        return List.of("antrim", "armagh", "carlow", "cavan", "clare", "cork", "derry", "donegal", "down", "dublin", "fermanagh", "galway", "kerry", "kildare", "kilkenny", "laois", "leitrim", "limerick", "longford", "louth", "mayo", "meath", "monaghan", "offaly", "roscommon", "sligo", "tipperary", "tyrone", "waterford", "westmeath", "wexford", "wicklow");
    }
}
