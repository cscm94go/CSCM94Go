package controllers;
import models.Users;
import javafx.fxml.FXML;
import javafx.stage.Window;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.javalite.activejdbc.Base;


//import static com.sun.activation.registries.LogSupport.log;

public class AccountController {

    String username_exist="false";
    Boolean login_success =false;

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

    @FXML
    private TextField usernameField;

    @FXML
    private Button submitButton;

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
}
