package controllers;

import application.Main;
import com.jfoenix.controls.JFXButton;
import helpers.HelperMethods;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.Admin;
import models.Users;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This class can create and delete users
 * or make administrator an existing user.
 * @author Hector
 * @version 1.0
 */
public class MakeAdminController implements Initializable {
    private static MakeAdminController instance;

    //Constructor for Admin Controller
    public MakeAdminController() {
        instance = this;
    }

    //Instance method for Admin Controller
    public static MakeAdminController getInstance()
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

    @FXML
    void handleBack(ActionEvent event) throws IOException {
        HelperMethods.LoadScene("/fxml/AdminOptions.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTable();
    }

    private void setTable(){
        tableView.getItems().remove(0, tableView.getItems().size());
        tableView.getColumns().remove(0, tableView.getColumns().size());
        final List<Users> us = Admin.getUsersList();

        TableColumn<Object, String> column1 = new TableColumn<Object, String>("User Name");
        column1.setPrefWidth(150);
        column1.setCellValueFactory(cell->{
            Users u = (Users) cell.getValue();
            return new ReadOnlyStringWrapper (u.username);
        });

        TableColumn<Object, String> column2 = new TableColumn<Object, String>("First Name");
        column2.setCellValueFactory(cell->{
            Users u = (Users) cell.getValue();
            return new ReadOnlyStringWrapper (u.firstname);
        });
        column2.setPrefWidth(150);

        TableColumn<Object, String> column3 = new TableColumn<Object, String>("Last Name");
        column3.setCellValueFactory(cell->{
            Users u = (Users) cell.getValue();
            return new ReadOnlyStringWrapper (u.lastname);
        });
        column3.setPrefWidth(150);

        TableColumn<Object, Boolean> column4 = new TableColumn<Object, Boolean>("isAdmin");
        column4.setCellValueFactory(cell -> {
            boolean isAdmin = cell.getValue() instanceof Admin;
            return new ReadOnlyBooleanWrapper(isAdmin);
        });

        column4.setCellFactory(p -> {
            CheckBoxTableCell cell = (CheckBoxTableCell) CheckBoxTableCell.forTableColumn(column4).call(p);


            cell.setOnMouseClicked(e -> {

                try {
                    int i = cell.getIndex();
                    String u = us.get(i).username;
                    Path path = Paths.get("users", u + ".json");
                    String jsonString = new String(Files.readAllBytes(path));
                    JSONObject json = new JSONObject(jsonString);
                    if (json.has("isAdmin")) {
                        json.remove("isAdmin");
                        json.remove("adminNumber");
                    } else {
                        json.put("isAdmin", true);
                        int max = 0;
                        for (int j = 0; j < us.size(); j++) {
                            if (us.get(j) instanceof Admin) {
                                Admin a = (Admin) us.get(j);
                                if (a.getAdminNumber() > max) max = a.getAdminNumber();
                            }
                        }
                        json.put("adminNumber", max);
                    }
                    BufferedWriter writer = new BufferedWriter(new FileWriter("users/" + u + ".json"));
                    writer.write(json.toString());
                    writer.close();
                    setTable();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            return cell;
        });

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);


        for (int i = 0; i < us.size(); i++) {
            Users u = us.get(i);
            tableView.getItems().add(u);
        }
    }





}
