package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;


public class Controller {

    public Button button;

    @FXML
    public void initialize() throws Exception{


        button.addEventFilter(MouseEvent.MOUSE_CLICKED, e ->  {

            Stage stage = (Stage) button.getScene().getWindow();

            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("../inGame/inGame.fxml"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });

    }

}

