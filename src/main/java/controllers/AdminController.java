package controllers;
import application.Main;
import com.jfoenix.controls.JFXButton;
import helpers.HelperMethods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.Admin;
import models.Users;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.Date;
import java.util.ResourceBundle;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
/**
 * This class can create and delete users
 * or make administrator an existing user.
 * @author Hector
 * @version 1.0
 */
public class AdminController implements Initializable {
    private static AdminController instance;

    //Constructor for Admin Controller
    public AdminController() {
        instance = this;
    }

    //Instance method for Admin Controller
    public static AdminController getInstance()
    {
        return instance;
    }
    public Admin admin;


    /**
     * This gives administration privileges to a user.
     * @param  user The user to be administrator.
     */
    public void makeAdmin(Users user){

        Admin admin = new Admin(user);
        storeAdmin(admin);
        delUser(user);
    }
    /**
     * This creates a new user, with no image,
     * and default values to 0.
     * @param name The user name.
     * @param fname The user first name.
     * @param lname The user last name.
     */
    public boolean newUser(String name,String fname, String lname) throws IOException {
        long count = Files.list(new File("users").toPath()).count();
        Users user = new Users();
        user.username = name;
        user.firstname = fname;
        user.lastname = lname;
        user.image = "";
        user.lastLogin = 0;
        user.registerTime = new Date().getTime();
        user.position = count + 1;
        user.store();
        return true;
    }
    /**
     * This deletes a user.
     * @param user User to be deleted.
     */
    public void delUser(Users user){

        File file = new File("users/" + user.username + ".json");
        if(file.delete()){
            System.out.println(user.username + " deleted");
        }else System.out.println(user.username +" doesn't exist");
    };
    /**
     * Save the administrator users to local file.
     */
    public void storeAdmin(Admin admin)  {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("users/admins/" + admin.username + ".json"));
            writer.write(admin.toJson());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setUserImage(String imagePath) {
        Image image = new Image(imagePath);
        userImage.setImage(image);
    }

    @FXML
    TableView games;

    @FXML
    Label positionChange;

    @FXML
    TableView newPlayers;

    @FXML
    private TableView tableView;

    @FXML
    private JFXButton createUser;

    @FXML
    private JFXButton backtoDashboard;

    @FXML
    private JFXButton makeAdmin;


    @FXML
    private ImageView userImage;

    @FXML
    private TextField user_name;

    @FXML
    private TextField last_name;

    @FXML
    private Button createButton;


    @FXML
    private TextField first_name;



    @FXML
    void handleDashboardButtonAction(ActionEvent event) throws IOException {
        HelperMethods.LoadScene("/fxml/AdminDashboard.fxml");
    }

    @FXML
    void handleMakeAdminButtonAction(ActionEvent event) throws IOException {
        HelperMethods.LoadScene("/fxml/MakeAdmin.fxml");
    }

    @FXML
    void handleCreateUserButtonAction(ActionEvent event) throws IOException {
        HelperMethods.LoadScene("/fxml/RegisterUser.fxml");
    }

    @FXML
    void handleCreateButtonAction(ActionEvent event) throws IOException {
        String firstName = first_name.getText();
        String lastName = last_name.getText();
        String username = user_name.getText();

        newUser(firstName, lastName, username);
//        HelperMethods.LoadScene("/fxml/application.fxml");
        if(firstName.isEmpty() || lastName.isEmpty() || username.isEmpty()) {
            HelperMethods.showAlert(Alert.AlertType.ERROR, Main.stage, "Form Error!", "Please ensure all fields are right!");
            return;
        }
        else if (newUser(firstName, lastName, username) == true) {
            HelperMethods.showAlert(Alert.AlertType.CONFIRMATION, Main.stage, "Registration Success!",
                    "New user " + user_name.getText() + " has been created.");
        }
        else  {
            HelperMethods.showAlert(Alert.AlertType.ERROR, Main.stage, "Registration Failure, user exists!",
                    "Please enter a unique username");
        }

    }

    /**
     * This sets the right image for the avatar clicked.
     * @param  event The mouse click to choose the avatar.
     */
    @FXML protected void avatar1Clicked(MouseEvent event) {
        String imagePath = "images/avataaars.png";
        setUserImage(imagePath);
    }
    /**
     * This sets the right image for the avatar clicked.
     * @param  event The mouse click to choose the avatar.
     */
    @FXML protected void avatar2Clicked(MouseEvent event) {
        String imagePath = "images/avataaars2.png";
        setUserImage(imagePath);
    }
    /**
     * This sets the right image for the avatar clicked.
     * @param  event The mouse click to choose the avatar.
     */
    @FXML protected void avatar3Clicked(MouseEvent event) {
        String imagePath = "images/avataaars3.png";
        setUserImage(imagePath);
    }
    /**
     * This sets the right image for the avatar clicked.
     * @param  event The mouse click to choose the avatar.
     */
    @FXML protected void avatar4Clicked(MouseEvent event) {
        String imagePath = "images/avataaars4.png";
        setUserImage(imagePath);
    }
    /**
     * This sets the right image for the avatar clicked.
     * @param  event The mouse click to choose the avatar.
     */
    @FXML protected void avatar5Clicked(MouseEvent event) {
        String imagePath = "images/avataaars5.png";
        setUserImage(imagePath);
    }
    /**
     * This sets the right image for the avatar clicked.
     * @param  event The mouse click to choose the avatar.
     */
    @FXML protected void avatar6Clicked(MouseEvent event) {
        String imagePath = "images/avataaars6.png";
        setUserImage(imagePath);
    }
    /**
     * This sets the right image for the avatar clicked.
     * @param  event The mouse click to choose the avatar.
     */
    @FXML protected void avatar7Clicked(MouseEvent event) {
        String imagePath = "images/avataaars7.png";
        setUserImage(imagePath);
    }
    /**
     * This sets the right image for the avatar clicked.
     * @param  event The mouse click to choose the avatar.
     */
    @FXML protected void avatar8Clicked(MouseEvent event) {
        String imagePath = "images/avataaars8.png";
        setUserImage(imagePath);
    }
    /**
     * This sets the right image for the avatar clicked.
     * @param  event The mouse click to choose the avatar.
     */
    @FXML protected void avatar9Clicked(MouseEvent event) {
        String imagePath = "images/avataaars9.png";
        setUserImage(imagePath);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn users = new TableColumn("Username");
        TableColumn checkAdmin = new TableColumn("Make Admin");
        users.setPrefWidth(500);
        checkAdmin.setPrefWidth(200);

        tableView.getColumns().addAll(users, checkAdmin);

        final ObservableList<String> data = FXCollections.observableArrayList(
                new Users().usersList.toString());

        users.setCellValueFactory(
                new PropertyValueFactory<Users,JSONObject>("username")
        );

        checkAdmin.setCellValueFactory(
                new PropertyValueFactory<Users,String>("makeAdmin")
        );

        tableView.setItems(data);
    }





}
