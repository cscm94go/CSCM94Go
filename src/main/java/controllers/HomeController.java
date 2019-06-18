package controllers;

import com.jfoenix.controls.JFXButton;
import helpers.HelperMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Window;

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
    Button adminBtn;

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
    @FXML
    public void initialize (URL location, ResourceBundle resources) {
        setUsername(AccountController.getInstance().username());

    }


}
