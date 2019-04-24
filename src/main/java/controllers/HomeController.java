package controllers;
import com.jfoenix.controls.JFXButton;
import helpers.HelperMethods;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.stage.Window;
import models.Record;
import models.Users;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
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
    TableView games;

    @FXML
    Label positionChange;

    @FXML
    TableView newPlayers;

    @FXML
    JFXButton play;

    @FXML
    JFXButton leaderBoard;

    @FXML
    JFXButton my_stats;

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


//    @FXML


    public void setUsername(String user) {
        this.welcomeUser.setText("Welcome, " + user + "!");
    }


    public void setUserimage() {
        profile_image.setImage(new Image(Users.currentUser.image));            // <---- causing null pointer
    }

    @FXML
    public void initialize (URL location, ResourceBundle resources) {
        setUserimage();
        setUsername(AccountController.getInstance().username());
        try {
            InfoSinceLastLogin.RecentActivity(games, newPlayers, positionChange);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
