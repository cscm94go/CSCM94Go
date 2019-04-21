package controllers;
import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import models.Record;
import models.Users;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.jws.soap.SOAPBinding;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class HomeController {

    @FXML
    Button btn;
    @FXML
    Button leaderBoard;
    @FXML
    VBox history;

    @FXML
    public void initialize() throws Exception{

        btn.addEventFilter(MouseEvent.MOUSE_CLICKED, a -> {
            try {
                Parent p = FXMLLoader.load(getClass().getResource("/fxml/GameBoard.fxml"));
                Scene board = new Scene(p, 1100, 900);
                Main.stage.setScene(board);
                Main.stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        leaderBoard.addEventFilter(MouseEvent.MOUSE_CLICKED, a -> {
            try {
                Parent p = FXMLLoader.load(getClass().getResource("/fxml/LeadBoard.fxml"));
                Scene board = new Scene(p, 1100, 900);
                Main.stage.setScene(board);
                Main.stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });



        String content = new String(Files.readAllBytes( Paths.get("playerRecords.json")), "UTF-8");
        JSONArray json = new JSONArray(content);

        List<Record> rs = new ArrayList<Record>();
        json.forEach(e -> {
            JSONObject j = (JSONObject) e;
            Record r = new Record(j);
            rs.add(r);
        });

        rs.stream().filter(r ->
            r.player1.equals(Users.currentUser.username) || r.player2.equals(Users.currentUser.username)
        ).forEach(r -> {
            Label l = new Label();
            String opponentUserName = r.player1 == Users.currentUser.username
                    ? r.player2
                    : r.player1;
            String status = r.winner.equals(Users.currentUser.username)
                    ? "won"
                    : "lost";
            l.setText("played with " + opponentUserName + ", and " + status);
            history.getChildren().add(l);
        });

    }

}
