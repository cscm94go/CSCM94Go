package controllers;
import com.jfoenix.controls.JFXButton;
import helpers.HelperMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.stage.Window;
import models.Record;
import models.Users;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Short one line description.
 * @author Zakariye Ali
 * @version
 *
 */

public class HomeController implements Initializable {

    private static HomeController instance;

    //Constructor for Account Controller
    public HomeController() {
        instance = this;
    }

    //Instance method for Account Controller
    public static HomeController getInstance()
    {
        return instance;
    }

    @FXML
    JFXButton play;
    @FXML
    JFXButton leaderBoard;
    @FXML
    JFXButton my_stats;
    @FXML
    JFXButton infoSinceLastLogin;
    @FXML
    VBox history;

    @FXML
    JFXButton logout;

    @FXML
    Label welcomeUser;

    @FXML
    ImageView profile_image;


    @FXML
    protected void handleLeaderboardButtonAction(ActionEvent event) throws IOException {
        HelperMethods.LoadScene("/fxml/Leaderboard.fxml");
    }

    @FXML
    protected void handleMyStatsButtonAction(ActionEvent event) throws IOException {
        HelperMethods.LoadScene("/fxml/MyProfile.fxml");
    }

    @FXML
    protected void handleInfoButtonAction(ActionEvent event) throws IOException {
        HelperMethods.LoadScene("/fxml/infoSinceLastLogin.fxml");
    }

    @FXML
    protected void handlePlayButtonAction(ActionEvent event) throws IOException {
        HelperMethods.LoadScene("/fxml/GameBoard.fxml");
    }

    @FXML
    protected void handleLogoutButtonAction(ActionEvent event) throws IOException {
        Window owner = logout.getScene().getWindow();
        HelperMethods.showAlert(Alert.AlertType.CONFIRMATION, owner, "Logged out",
                "Logged out successfully!");
        HelperMethods.LoadScene("/fxml/application.fxml");
    }

    @FXML
    void handleAdminButtonAction(ActionEvent event) throws IOException {
        HelperMethods.LoadScene("/fxml/AdminOptions.fxml");
    }


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

    public void setUsername(String user) {
        this.welcomeUser.setText("Welcome, " + user + "!");
    }


    public void setUserimage() {
        profile_image.setImage(new Image(Users.currentUser.image));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        setUserimage();          <---- get null pointer exception error
        setUsername(AccountController.getInstance().username());
    }

}
