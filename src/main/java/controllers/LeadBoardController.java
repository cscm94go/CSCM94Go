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
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LeadBoardController {

    @FXML
    Button btn1;
    @FXML
    Button btn2;
//    @FXML
//    Button leaderBoard;
    @FXML
    VBox vBox;

    @FXML
    public void initialize() throws Exception{
        firstSort();

        btn1.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, a -> {
            try {
                firstSort();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        btn2.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, a -> {
            try {
                secondSort();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    void firstSort() throws IOException {

        String content = new String(Files.readAllBytes( Paths.get("playerRecords.json")), "UTF-8");
        JSONArray json = new JSONArray(content);

        List<Record> rs = new ArrayList<Record>();
        json.forEach(e -> {
            JSONObject j = (JSONObject) e;
            Record r = new Record(j);
            rs.add(r);
        });

        HashSet<String> users = new HashSet<>();
        rs.stream().forEach(r -> {
            users.add(r.player1);
            users.add(r.player2);
        });

        users.stream().forEach(u -> {
//            return 1;
            AtomicInteger win = new AtomicInteger();
            AtomicInteger lose = new AtomicInteger();
            rs.stream().forEach(r -> {
                if (r.player1.equals(u) || r.player2.equals(u)) {
                    if (r.winner.equals(u)) win.getAndIncrement();
                    else lose.getAndIncrement();
                }
            });

            HBox h = new HBox();
            Label l1 = new Label();
            l1.setText(u);
            Label l2 = new Label();
            double percentage = win.doubleValue() / (win.doubleValue() + lose.doubleValue());
            l2.setText(Double.toString(percentage));
            h.getChildren().add(l1);
            h.getChildren().add(l2);
            vBox.getChildren().add(h);
        });
    }

    void secondSort() throws IOException {

        String content = new String(Files.readAllBytes( Paths.get("playerRecords.json")), "UTF-8");
        JSONArray json = new JSONArray(content);

        List<Record> rs = new ArrayList<Record>();
        json.forEach(e -> {
            JSONObject j = (JSONObject) e;
            Record r = new Record(j);
            rs.add(r);
        });

        HashSet<String> users = new HashSet<>();
        rs.stream().forEach(r -> {
            users.add(r.player1);
            users.add(r.player2);
        });

        users.stream().forEach(u -> {
            AtomicInteger win = new AtomicInteger();
            rs.stream().forEach(r -> {
                if (r.winner.equals(u)) win.getAndIncrement();
            });
            HBox h = new HBox();
            Label l1 = new Label();
            l1.setText(u);
            Label l2 = new Label();
            l2.setText(Integer.toHexString(win.get()));
            h.getChildren().add(l1);
            h.getChildren().add(l2);
            vBox.getChildren().add(h);
        });
    }

}
