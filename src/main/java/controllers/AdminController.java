package controllers;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import models.Admin;
import models.Users;
import org.javalite.activejdbc.Model;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.sql.*;
import java.util.ResourceBundle;


public class AdminController {
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
    void handleDashboardButtonAction(ActionEvent event) {

    }

    @FXML
    void handleMakeAdminButtonAction(ActionEvent event) {

    }

    @FXML
    void handleCreateUserButtonAction(ActionEvent event) {

    }









    public void makeAdmin(String userName) throws IOException {
        Users user = Users.currentUser;
        if (AccountController.getInstance().isAdmin(userName) == true) {

        } else {
            SimpleObjectProperty<Date> joinDate = new SimpleObjectProperty<>(this, "joinDate", new Date());
            int adminNumber =
            admin = new Admin((user.username), joinDate.get(), adminNumber);
            admin.store();
        }
    }



    public void showAdminDetails(Admin admin) {

//        System.out.println("Name: " + admin.getUserName() );

    }

}









