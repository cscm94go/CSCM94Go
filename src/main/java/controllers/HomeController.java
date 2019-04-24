package controllers;
import com.jfoenix.controls.JFXButton;
import helpers.HelperMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.stage.Window;
import models.Admin;
import models.Users;

import java.io.IOException;
import java.net.URL;
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
    Button adminBtn;


    @FXML
    protected void handleLeaderboardButtonAction(ActionEvent event) throws IOException {
        HelperMethods.LoadScene("/fxml/Leaderboard.fxml");
    }

    @FXML
    protected void handleMyStatsButtonAction(ActionEvent event) throws IOException {
        HelperMethods.LoadScene("/fxml/MyRecords.fxml");
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
    void handleInfoButtonAction(ActionEvent event) throws IOException {
        HelperMethods.LoadScene("/fxml/AdminOptions.fxml");
    }





//    @FXML


    public void setUsername(String user) {
        this.welcomeUser.setText("Welcome, " + user + "!");
    }


    public void setUserimage() {
        profile_image.setImage(new Image(Users.currentUser.image));
    }

    @FXML
    public void initialize (URL location, ResourceBundle resources) {
        setUserimage();

        adminBtn.setVisible(Admin.currentAdmin != null);
        setUsername(AccountController.getInstance().username());
        try {
            InfoSinceLastLogin.RecentActivity(games, newPlayers, positionChange);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
