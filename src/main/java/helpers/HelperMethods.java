package helpers;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Window;

import java.io.IOException;

public class HelperMethods {
    public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    public static void LoadScene(String resource) throws IOException {
        Parent parent = FXMLLoader.load(HelperMethods.class.getResource(resource));
        Scene home = new Scene(parent , 1100, 900);
        Main.stage.setScene(home);
        Main.stage.show();
    }
}
