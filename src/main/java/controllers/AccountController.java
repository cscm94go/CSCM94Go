package controllers;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import models.Users;
import javafx.fxml.FXML;
import javafx.stage.Window;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.javalite.activejdbc.Base;

import javax.xml.soap.Text;


//import static com.sun.activation.registries.LogSupport.log;

public class AccountController {

    private Scene firstScene;
    private Scene secondScene;
    private Node node;
    private Boolean register_success =false;
    String username_exist="false";
    Boolean login_success =false;

    public void setFirstScene(Scene scene) {
        firstScene = scene;
    }
    public void setSecondScene(Scene scene) {
        secondScene = scene;
    }
    public void openFirstScene(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(firstScene);
    }

    public void openSecondScene(ActionEvent actionEvent) {
        Stage primaryStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(secondScene);
    }



    public boolean login(String usernameField) {
        try {
            Base.open("com.mysql.cj.jdbc.Driver", "JDBC:mysql://remotemysql.com:3306/dTXt3FVdSy", "dTXt3FVdSy", "s4dL5PTH35");
            Users u = Users.findById(5);
            String username = u.getUserName();
            if (usernameField.equals(username))
            {
                username_exist="true";
                login_success = true;
            }

            Base.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return login_success;
    }

    public boolean registerUser(String firstName, String lastName, String userName) {
        try {
            Base.open("com.mysql.cj.jdbc.Driver", "JDBC:mysql://remotemysql.com:3306/dTXt3FVdSy", "dTXt3FVdSy", "s4dL5PTH35");
//            String sql = "INSERT INTO USERS(firstName, lastName, userName)";
            Users u = new Users();
            u.set("firstname", firstName);
            u.set("lastname", lastName);
            u.set("username", userName);
            u.saveIt();
            String newUser = u.getId().toString();
            if (newUser != null) {
                register_success = true;
            }
            Base.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return register_success;
    }

    @FXML
    private  TextField first_name;

    @FXML
    private TextField last_name;

    @FXML
    private  TextField user_name;


    @FXML
    private TextField usernameField;

    @FXML
    private Button submitButton;

    @FXML
    private Hyperlink registerLink;

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
        String user= usernameField.getText();
        login(user);
        Window owner = submitButton.getScene().getWindow();
        if(usernameField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your name");
            return;
        }
        else if (login(user) == true) {
            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Enter!",
                    "Welcome " + usernameField.getText());
        }
        else
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "User can not be found! Try again.");
    }

    @FXML
    protected void handleClickAction(ActionEvent actionEvent) {
        System.out.println("Register a new user!");
        openSecondScene(actionEvent);
    }

    @FXML
    protected void handleCreateButtonAction(ActionEvent event) {
        String firstName = first_name.getText();
        String lastName = last_name.getText();
        String username = user_name.getText();
        registerUser(firstName, lastName, username);
        openSecondScene(event);
        Window owner = registerLink.getScene().getWindow();
        if(firstName.isEmpty() || lastName.isEmpty() || username.isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "Please ensure all fields are right!");
            return;
        }
        else if (registerUser(firstName, lastName, username) == true) {
            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Registration Success!",
                    "New user " + user_name.getText() + " has been created.");
//            openFirstScene(event);
        }
    }


}
