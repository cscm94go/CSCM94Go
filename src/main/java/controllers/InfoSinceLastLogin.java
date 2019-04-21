package controllers;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Record;
import models.Users;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class InfoSinceLastLogin {

    @FXML
    VBox games;
    @FXML
    Label positionChange;
    @FXML
    VBox newPlayers;


    @FXML
    public void initialize() throws Exception{

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
            Label l = new Label();
            l.setText(r.player1 + " played with " + r.player2 + ", and " + r.winner + " won, time: " + new Date(r.timeStamp).toLocaleString());
            games.getChildren().add(l);
        });

        int newPosition = LeadBoardController.sort().indexOf(Users.currentUser.username);

        positionChange.setText("position change from " + Users.currentUser.position + " to " + newPosition);

//        positionChange

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
                        Label l = new Label();
                        l.setText(u.username + "joined in " + new Date(u.registerTime).toLocaleString());
                        newPlayers.getChildren().add(l);
                    });

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

}
