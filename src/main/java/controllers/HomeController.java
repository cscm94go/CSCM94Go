package controllers;

import com.jfoenix.controls.JFXButton;
import helpers.HelperMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Window;
import models.Admin;
import models.Users;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * The controller for navigating the home dashboard once logged in.
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

    /**
     * This is the table view for recent games of players
     */
    @FXML
    TableView games;
    /**
     * This is the label to show position change since last logged in
     */
    @FXML
    Label positionChange;
    /**
     * This is the table view for new players to have joined
     */
    @FXML
    TableView newPlayers;
    /**
     * This is the button to play the game
     */
    @FXML
    JFXButton play;
    /**
     * This is the button to see leaderboard.
     */
    @FXML
    JFXButton leaderBoard;
    /**
     * This is the button to see stats and profile.
     */
    @FXML
    JFXButton my_stats;
    /**
     * This is the logout button.
     */
    @FXML
    JFXButton logout;
    /**
     * This is the label to show username of current user.
     */
    @FXML
    Label welcomeUser;
    /**
     * Display current user's profile image.
     */
    @FXML
    public ImageView profile_image;
    /**
     * Display admin button when admin logged in.
     */
    @FXML
    Button adminBtn;

    /**
     * Return leaderboard view
     */
    @FXML
    protected void handleLeaderboardButtonAction(ActionEvent event) throws IOException {
        HelperMethods.LoadScene("/fxml/Leaderboard.fxml");
    }
    /**
     * Return my stats view
     */
    @FXML
    protected void handleMyStatsButtonAction(ActionEvent event) throws IOException {
        HelperMethods.LoadScene("/fxml/MyStats.fxml");
    }
    /**
     * Return Game view.
     */
    @FXML
    protected void handlePlayButtonAction(ActionEvent event) throws IOException {
        HelperMethods.LoadScene("/fxml/GameBoard.fxml");
    }
    /**
     * Return log in screen after log out.
     */
    @FXML
    protected void handleLogoutButtonAction(ActionEvent event) throws IOException {
        Window owner = logout.getScene().getWindow();
        HelperMethods.showAlert(Alert.AlertType.CONFIRMATION, owner, "Logged out",
                "Logged out successfully!");
        HelperMethods.LoadScene("/fxml/application.fxml");
    }
    /**
     * Load admin options
     */
    @FXML
    void handleAdminButtonAction(ActionEvent event) throws IOException {
        HelperMethods.LoadScene("/fxml/AdminOptions.fxml");
    }
    /**
     * Set label to username of current user
     */
    public void setUsername(String user) {
        this.welcomeUser.setText("Welcome, " + user + "!");
    }

    /**
     * Set profile image to current user's.
     */
    public void setUserimage() {
        profile_image.setImage(new Image(Users.currentUser.image));
    }
    /**
     * Initializer for JavaFXML document.
     */
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
