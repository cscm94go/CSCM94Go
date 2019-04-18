package controllers;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Users;
import javafx.fxml.FXML;
import javafx.stage.Window;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.javalite.activejdbc.Base;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


//import static com.sun.activation.registries.LogSupport.log;

public class AccountController {

    private Scene firstScene;
    private Scene secondScene;
    private Node node;
    private Boolean register_success =false;
    private List<String> list = new ArrayList<String>();
    ImageView imageView;
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


    public String[] getImages() {
        File file = new File(String.valueOf(getClass().getResource("~/resources/fxml/images").getFile()));
        String[] imagesList = file.list();
        return imagesList;
    }

    public boolean login(String user) {
        try {
            String url = "JDBC:mysql://remotemysql.com:3306/dTXt3FVdSy";
            Connection conn = DriverManager.getConnection(url, "dTXt3FVdSy", "s4dL5PTH35");
            Statement st = conn.createStatement();
            ResultSet rs;
            rs = st.executeQuery("SELECT username from users");
            while (rs.next()) {
                String userName = rs.getString("username");
                if (user.equals(userName)) {
                    username_exist="true";
                    login_success = true;
                }
            }
            conn.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return login_success;
    }

    public boolean registerUser(String firstName, String lastName, String userName) {
        int count;
        try {
            Base.open("com.mysql.cj.jdbc.Driver", "JDBC:mysql://remotemysql.com:3306/dTXt3FVdSy", "dTXt3FVdSy", "s4dL5PTH35");
            List<Users> users = Users.where("username = 'userName'");
//            String[] imagesList = getImages();
            if (users.isEmpty()) {
                Users u = new Users();
                u.set("firstname", firstName);
                u.set("lastname", lastName);
                u.set("username", userName);
                u.saveIt();
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
    private Button createButton;

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
        Window owner = createButton.getScene().getWindow();
        openFirstScene(event);
        if(firstName.isEmpty() || lastName.isEmpty() || username.isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "Please ensure all fields are right!");
            return;
        }
        else if (registerUser(firstName, lastName, username) == true) {
            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Registration Success!",
                    "New user " + user_name.getText() + " has been created.");
        }
        else  {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Registration Failure, user exists!",
                    "Please enter a unique username");
        }
    }

//
//    protected void handleSelectImageAction(ActionEvent actionEvent) {
//
//    }
}
