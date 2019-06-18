package controllers;

import application.Main;
import com.jfoenix.controls.JFXButton;
import helpers.HelperMethods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import models.Users;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * The controller for managing logging in, registering yourself as user.
 * @author Zakariye Ali
 * @version 1.0
 *
 */
public class AccountController implements Initializable {
    /**
     * Instantiate AccountController
     */
    private static AccountController instance;

    /**
     * Constructor for Account Controller
     */
    public AccountController() {
        instance = this;
    }

    /**
     * @return A Account Controller class (no instance).
     */
    public static AccountController getInstance()
    {
        return instance;
    }
    /**
     * @return The user's name.
     */
    public String username() {
        return  user_name.getText();
    }


    public boolean login(String user) {

        try {
            userSearch(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     *
     * @param  user User to be found.
     */
    private void userSearch(String user) throws IOException {
        Path thePath = Files.list(new File("users").toPath())
                .filter(path -> path.equals(Paths.get("users", user + ".json"))).findFirst().get();
        String content = new String(Files.readAllBytes(thePath), "UTF-8");
        Users u = new Users(content);
        Users.currentUser = u;
    }

    /**
     * Short one line description.
     * @param firstName The user's first name.
     * @param lastName The user's last name.
     * @param userName The user's name to login.
     * @return Description text text text.
     */
    public boolean registerUser(String firstName, String lastName, String userName) {

        try {
            long count = Files.list(new File("users").toPath()).count();
            Users u = new Users();
            u.firstname = firstName;
            u.lastname = lastName;
            u.username = userName;
            u.store();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * This sets the right image for the avatar clicked.
     * @param  event The mouse click to choose the avatar.
     */

    /**
     * This is text field for first name
     */
    @FXML
    private  TextField first_name;
    /**
     * This is text field for last name
     */
    @FXML
    private TextField last_name;
    /**
     * This is text field for username
     */
    @FXML
    private  TextField user_name;
    /**
     * This is the log-in button.
     */
    @FXML
    private Button submitButton;
    /**
     * This is the create button after new user details have been entered.
     */
    @FXML
    private Button createButton;
    /**
     * The log-out button
     */
    @FXML
    private JFXButton log_out;
    /**
     * To get to register user screen use Register Button.
     */
    @FXML
    private JFXButton registerButton;
    /**
     * Log in to dashboard
     * @param  event Return Home Dashboard scene
     * @throws IOException
     */
    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) throws IOException {
        String user= username();
        login(user);
        if(user_name.getText().isEmpty()) {
            HelperMethods.LoadScene("/fxml/HomeDashboard.fxml");
            return;
        }
        else if (login(user) == true) {
                HelperMethods.LoadScene("/fxml/HomeDashboard.fxml");
            HelperMethods.showAlert(Alert.AlertType.CONFIRMATION, Main.stage, "Enter!",
                    "Welcome " + user_name.getText());
        }
        else
            HelperMethods.showAlert(Alert.AlertType.ERROR, Main.stage, "Form Error!", "User can not be found! Try again.");
    }
    /**
     * Handles the button to navigate to Register User from Home.
     * @param  actionEvent Returns Register User scene
     */
    public void handleRegisterButton(ActionEvent actionEvent) throws IOException {
        Window owner = registerButton.getScene().getWindow();
        HelperMethods.showAlert(Alert.AlertType.CONFIRMATION, owner, "Create a User!",
                "Enter User details");
        HelperMethods.LoadScene("/fxml/RegisterUser.fxml");
    }

    /**
     * Handles the create a user button during register.
     * @param  event If registration successful, return to Log in.
     */
    @FXML
    protected void handleCreateButtonAction(ActionEvent event) throws IOException {
        String firstName = first_name.getText();
        String lastName = last_name.getText();
        String username = user_name.getText();

        registerUser(firstName, lastName, username);
        HelperMethods.LoadScene("/fxml/application.fxml");
        if(firstName.isEmpty() || lastName.isEmpty() || username.isEmpty()) {
            HelperMethods.showAlert(Alert.AlertType.ERROR, Main.stage, "Form Error!", "Please ensure all fields are right!");
            return;
        }
        else if (registerUser(firstName, lastName, username) == true) {
            HelperMethods.showAlert(Alert.AlertType.CONFIRMATION, Main.stage, "Registration Success!",
                    "New user " + user_name.getText() + " has been created.");
        }
        else  {
            HelperMethods.showAlert(Alert.AlertType.ERROR, Main.stage, "Registration Failure, user exists!",
                    "Please enter a unique username");
        }
    }
    /**
     * Handle click back button event
     */
    public void handleBackButtonAction(ActionEvent actionEvent) throws IOException {
        HelperMethods.LoadScene("/fxml/application.fxml");
    }
    /**
     * Initializer
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
