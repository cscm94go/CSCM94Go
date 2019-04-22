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

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Short one line description.
 * @author
 * @version
 *
 */
public class LeadBoardController {

    @FXML
    Button btn1;
    @FXML
    Button btn2;
    @FXML
    Button back;
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
        back.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, a -> {

                try {
                    Parent p = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
                    Scene board = new Scene(p, 1100, 900);
                    Main.stage.setScene(board);
                    Main.stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        });
    }

    public static List<String> sort(){
        String content = null;
        try {
            content = new String(Files.readAllBytes( Paths.get("playerRecords.json")), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        List<String> us = users.stream().collect(Collectors.toList());
        us.sort((u1, u2) -> {
            AtomicInteger win = new AtomicInteger();
            AtomicInteger lose = new AtomicInteger();
            AtomicInteger win2 = new AtomicInteger();
            AtomicInteger lose2 = new AtomicInteger();
            rs.stream().forEach(r -> {
                if (r.player1.equals(u1) || r.player2.equals(u1)) {
                    if (r.winner.equals(u1)) win.getAndIncrement();
                    else lose.getAndIncrement();
                }
                if (r.player1.equals(u2) || r.player2.equals(u2)) {
                    if (r.winner.equals(u2)) win2.getAndIncrement();
                    else lose2.getAndIncrement();
                }
            });
            double percentage1 = win.doubleValue() / (win.doubleValue() + lose.doubleValue());
            double percentage2 = win2.doubleValue() / (win2.doubleValue() + lose2.doubleValue());
            return percentage1 > percentage2 ? 1 : -1;
        });
        return us;
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

        sort().stream().forEach(u -> {
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
