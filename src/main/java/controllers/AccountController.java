package controllers;
import application.Main;
import com.jfoenix.controls.JFXButton;
import helpers.HelperMethods;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import models.Users;
import javafx.fxml.FXML;
import javafx.stage.Window;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Short one line description.
 * @author Zakariye Ali
 * @version 1.0
 *
 */
public class AccountController implements Initializable {
    /**
     * Short one line description.
     */
    private Scene firstScene;
    /**
     * Short one line description.
     */
    private Scene secondScene;
    /**
     * Short one line description.
     */
    private Node node;
    /**
     * Short one line description.
     */
    private Boolean register_success =false;
    /**
     * Short one line description.
     */
    private List<String> list = new ArrayList<String>();
    /**
     * Short one line description.
     */
    private static AccountController instance;

    //Constructor for Account Controller
    /**
     * Short one line description.
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
        return  usernameField.getText();
    }

    public Image userimage() {
        Image image = new Image(Users.currentUser.image);
        return image;
    }
    /**
     *The user's image.
     */
    @FXML private ImageView userImage;

    String username_exist="false";
    Boolean login_success =false;


    /**
     * Short one line description.
     */
    private String selectedImagePath = "images/avataaars.png";
    /**
     * Short one line description.
     * @param  imagePath Description text text text.
     */
    private void setUserImage(String imagePath) {
        selectedImagePath = imagePath;
        Image image = new Image(imagePath);
        userImage.setImage(image);
    }
    /**
     * Short one line description.
     * @param  user Description text text text.
     * @return True if the user exists.
     */
    public boolean login(String user) {

        try {
            userSearch(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
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

//    public String userImage() {
//        return loggedinUser().image;
//    }
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

    /**
     * Short one line description.
     */
    @FXML
    private  TextField first_name;
    /**
     * Short one line description.
     */
    @FXML
    private TextField last_name;
    /**
     * Short one line description.
     */
    @FXML
    private  TextField user_name;
    /**
     * Short one line description.
     */
    @FXML
    private TextField usernameField;
    /**
     * Short one line description.
     */
    @FXML
    private Button submitButton;
    /**
     * Short one line description.
     */
    @FXML
    private Button createButton;
    /**
     * Short one line description.
     */
    @FXML
    private JFXButton log_out;

    @FXML
    private JFXButton registerButton;

    @FXML
    private Label welcomeUser;

    @FXML
    private ImageView profile_image;

    /**
     * Short one line description.
     * @param  event Description text text text.
     * @return Description text text text.
     * @throws IOException
     */
    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) throws IOException {
        String user= usernameField.getText();
        login(user);
        if(usernameField.getText().isEmpty()) {
            HelperMethods.showAlert(Alert.AlertType.ERROR, Main.stage, "Form Error!",
                    "Please enter your name");
            return;
        }
        else if (login(user) == true) {
            HelperMethods.LoadScene("/fxml/HomeDashboard.fxml");
            HelperMethods.showAlert(Alert.AlertType.CONFIRMATION, Main.stage, "Enter!",
                    "Welcome " + usernameField.getText());
        }
        else
            HelperMethods.showAlert(Alert.AlertType.ERROR, Main.stage, "Form Error!", "User can not be found! Try again.");
    }
    /**
     * Short one line description.
     * @param  actionEvent Description text text text.
     * @return Description text text text.
     */
    public void handleRegisterButton(ActionEvent actionEvent) throws IOException {
        Window owner = registerButton.getScene().getWindow();
        HelperMethods.showAlert(Alert.AlertType.CONFIRMATION, owner, "Create a User!",
                "Enter User details");
        HelperMethods.LoadScene("/fxml/RegisterUser.fxml");
    }
    /**
     * Short one line description.
     * @param  resource Description text text text.
     * @return Description text text text.
     * @throws IOException
     */
//    private void LoadScene(String resource) throws IOException {
//        Parent parent = FXMLLoader.load(getClass().getResource(resource));
//        Scene home = new Scene(parent , 1100, 900);
//        Main.stage.setScene(home);
//        Main.stage.show();
//    }
    /**
     * Short one line description.
     * @param  event Description text text text.
     * @return Description text text text.
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
     * Short one line description.
     * @param location Description text text text.
     * @param resources
     * @return Description text text text.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    public void handleBackButtonAction(ActionEvent actionEvent) throws IOException {
        HelperMethods.LoadScene("/fxml/application.fxml");
    }


//
//    protected void handleSelectImageAction(ActionEvent actionEvent) {
//
//    }
}
