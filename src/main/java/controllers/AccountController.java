package controllers;
import application.Main;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Users;
import javafx.fxml.FXML;
import javafx.stage.Window;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Short one line description.
 * @author Zak
 * @version
 *
 */

public class AccountController implements Initializable {

    private Scene firstScene;
    private Scene secondScene;
    private Node node;
    private Boolean register_success =false;
    private List<String> list = new ArrayList<String>();

    private static AccountController instance;

    //Constructor for Account Controller
    public AccountController() {
        instance = this;
    }

    //Instance method for Account Controller
    public static AccountController getInstance()
    {
        return instance;
    }

    public String username() {
        return  usernameField.getText();
    }

    @FXML private ImageView userImage;

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


    private String selectedImagePath = "images/avataaars.png";

    private void setUserImage(String imagePath) {
        selectedImagePath = imagePath;
        Image image = new Image(imagePath);
        userImage.setImage(image);
    }

    public boolean login(String user) {

        try {
            userSearch(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public Users loggedinUser(){
        try{
        String user = usernameField.getText();
            userSearch(user);
        } catch (Exception e) {
        System.out.println(e.getMessage());
        }
        return Users.currentUser;
    }

    private void userSearch(String user) throws IOException {
        Path thePath = Files.list(new File("users").toPath())
                .filter(path -> path.toString().equals("users/" + user + ".json")).findFirst().get();
        String content = new String(Files.readAllBytes(thePath), "UTF-8");
        Users u = new Users(content);
        Users.currentUser = u;
    }

//    public String userImage() {
//        return loggedinUser().image;
//    }


    public boolean registerUser(String firstName, String lastName, String userName) {

        try {
            long count = Files.list(new File("users").toPath()).count();
            Users u = new Users();
            u.firstname = firstName;
            u.lastname = lastName;
            u.username = userName;
            u.image = selectedImagePath;
            u.lastLogin = 0;
            u.registerTime = new Date().getTime();
            u.position = count + 1;
            u.store();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    @FXML protected void avatar1Clicked(MouseEvent event) {
        String imagePath = "images/avataaars.png";
        setUserImage(imagePath);
    }
    @FXML protected void avatar2Clicked(MouseEvent event) {
        String imagePath = "images/avataaars2.png";
        setUserImage(imagePath);
    }
    @FXML protected void avatar3Clicked(MouseEvent event) {
        String imagePath = "images/avataaars3.png";
        setUserImage(imagePath);
    }
    @FXML protected void avatar4Clicked(MouseEvent event) {
        String imagePath = "images/avataaars4.png";
        setUserImage(imagePath);
    }
    @FXML protected void avatar5Clicked(MouseEvent event) {
        String imagePath = "images/avataaars5.png";
        setUserImage(imagePath);
    }
    @FXML protected void avatar6Clicked(MouseEvent event) {
        String imagePath = "images/avataaars6.png";
        setUserImage(imagePath);
    }
    @FXML protected void avatar7Clicked(MouseEvent event) {
        String imagePath = "images/avataaars7.png";
        setUserImage(imagePath);
    }
    @FXML protected void avatar8Clicked(MouseEvent event) {
        String imagePath = "images/avataaars8.png";
        setUserImage(imagePath);
    }
    @FXML protected void avatar9Clicked(MouseEvent event) {
        String imagePath = "images/avataaars9.png";
        setUserImage(imagePath);
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
    private JFXButton log_out;


    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) throws IOException {
        String user= usernameField.getText();
        login(user);
        Window owner = submitButton.getScene().getWindow();
        if(usernameField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                    "Please enter your name");
            return;
        }
        else if (login(user) == true) {
            LoadScene("/fxml/Home.fxml");
            AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Enter!",
                    "Welcome " + usernameField.getText());
        }
        else
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "User can not be found! Try again.");
    }

    private void LoadScene(String resource) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(resource));
        Scene home = new Scene(parent , 1100, 900);
        Main.stage.setScene(home);
        Main.stage.show();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

//
//    protected void handleSelectImageAction(ActionEvent actionEvent) {
//
//    }
}
