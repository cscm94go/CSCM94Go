
package application;
import controllers.AccountController;
import javafx.scene.input.KeyEvent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
/**
 * This is the entry class
 *
 */
public class Main extends Application {

    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        FXMLLoader firstPaneLoader = new FXMLLoader(getClass().getResource("/fxml/MakeAdmin.fxml"));
        Parent firstPane = firstPaneLoader.load();
        Scene firstScene = new Scene(firstPane, 1100, 900);

        primaryStage.setTitle("Go");
        primaryStage.setScene(firstScene);
        primaryStage.show();
        firstPane.addEventFilter(KeyEvent.KEY_PRESSED, key  ->  {
            if (key.getText().equals("g")) {
                Scene board = null;
                try {
                    Parent p = FXMLLoader.load(getClass().getResource("/fxml/GameBoard.fxml"));
                    board = new Scene(p, 600, 600);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                primaryStage.setScene(board);
                primaryStage.show();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
