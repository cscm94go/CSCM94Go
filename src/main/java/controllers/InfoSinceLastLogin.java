package controllers;

import helpers.HelperMethods;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Record;
import models.Users;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InfoSinceLastLogin {

    @FXML
    TableView games;
    @FXML
    Label positionChange;
    @FXML
    TableView newPlayers;

    @FXML
    public void initialize() throws Exception{
        {
            TableColumn<Record, String> column1 = new TableColumn<Record, String>();
            column1.setText("Time");
            column1.setPrefWidth(200);
            column1.setCellValueFactory(cell -> {
                return new ReadOnlyStringWrapper(new Date(cell.getValue().timeStamp).toLocaleString());
            });

            TableColumn<Record, String> column2 = new TableColumn<Record, String>();
            column2.setText("Player1");
            column2.setPrefWidth(100);
            column2.setCellValueFactory(cell -> {
                return new ReadOnlyStringWrapper(cell.getValue().player1);
            });

            TableColumn<Record, String> column3 = new TableColumn<Record, String>();
            column3.setText("Playe2");
            column3.setPrefWidth(100);
            column3.setCellValueFactory(cell -> {
                return new ReadOnlyStringWrapper(cell.getValue().player2);
            });

            TableColumn<Record, String> column4 = new TableColumn<Record, String>();
            column4.setText("Winner");
            column4.setPrefWidth(100);
            column4.setCellValueFactory(cell -> {
                return new ReadOnlyStringWrapper(cell.getValue().winner);
            });


            games.getColumns().add(column1);
            games.getColumns().add(column2);
            games.getColumns().add(column3);
            games.getColumns().add(column4);
        }

        {
            TableColumn<Users, String> column1 = new TableColumn<Users, String>();
            column1.setText("Joined Date");
            column1.setPrefWidth(200);
            column1.setCellValueFactory(cell -> {
                return new ReadOnlyStringWrapper(new Date(cell.getValue().registerTime).toLocaleString());
            });

            TableColumn<Users, String> column2 = new TableColumn<Users, String>();
            column2.setText("First Name");
            column2.setPrefWidth(100);
            column2.setCellValueFactory(cell -> {
                return new ReadOnlyStringWrapper(cell.getValue().firstname);
            });

            TableColumn<Users, String> column3 = new TableColumn<Users, String>();
            column3.setText("Last Name");
            column3.setPrefWidth(100);
            column3.setCellValueFactory(cell -> {
                return new ReadOnlyStringWrapper(cell.getValue().lastname);
            });

            TableColumn<Users, String> column4 = new TableColumn<Users, String>();
            column4.setText("User Name");
            column4.setPrefWidth(100);
            column4.setCellValueFactory(cell -> {
                return new ReadOnlyStringWrapper(cell.getValue().username);
            });

            newPlayers.getColumns().add(column1);
            newPlayers.getColumns().add(column2);
            newPlayers.getColumns().add(column3);
            newPlayers.getColumns().add(column4);
        }



        String content = new String(Files.readAllBytes( Paths.get("playerRecords.json")), "UTF-8");
        JSONArray json = new JSONArray(content);

        List<Record> rs = new ArrayList<Record>();
        json.forEach(e -> {
            JSONObject j = (JSONObject) e;
            Record r = new Record(j);
            rs.add(r);
        });

        rs.stream().filter(r -> {
            return Users.currentUser.lastLogin < r.timeStamp;
        }).forEach(r -> {
            games.getItems().add(r);
        });

        int newPosition = LeadBoardController.sort(0).indexOf(Users.currentUser.username);
        positionChange.setText("position change from " + Users.currentUser.position + " to " + newPosition);

        try {
            Files.list(new File("users").toPath())
                    .map(path -> {
                        Users u = new Users();
                        try {
                            u = new Users(new String(Files.readAllBytes(path), "UTF-8"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return u;
                    })
                    .filter(u -> u.registerTime > Users.currentUser.lastLogin)
                    .forEach(u -> {
                        newPlayers.getItems().add(u);
                    });

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void onBack(ActionEvent actionEvent) {
        try {
            HelperMethods.LoadScene("/fxml/HomeDashboard.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
