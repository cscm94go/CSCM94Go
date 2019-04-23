package controllers;
import application.Main;
import com.jfoenix.controls.JFXButton;
import helpers.HelperMethods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import models.Admin;
import models.Users;
import org.json.JSONObject;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.List;
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
    public void newUser(String name,String fname, String lname){
        Users user = new Users();
        user.username = name;
        user.firstname = fname;
        user.lastname = lname;
        user.image = "";
        user.lastLogin = 0;
        user.registerTime = 0;
        user.position = 0;
        user.store();
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

    @FXML
    private TableView tableView;

    @FXML
    private ImageView profile_image;

    @FXML
    private JFXButton my_stats;

    @FXML
    private Label welcomeUser;

    @FXML
    private JFXButton createUser;

    @FXML
    private JFXButton backtoDashboard;

    @FXML
    private JFXButton makeAdmin;

    @FXML
    void handleMyStatsButtonAction(ActionEvent event) {

    }

    @FXML
    void handleDashboardButtonAction(ActionEvent event) throws IOException {
        HelperMethods.LoadScene("/fxml/AdminDashboard.fxml");
    }

    @FXML
    void handleMakeAdminButtonAction(ActionEvent event) {

    }

    @FXML
    void handleCreateUserButtonAction(ActionEvent event) {

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
