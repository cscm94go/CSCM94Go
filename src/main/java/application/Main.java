package application;

import javafx.scene.input.KeyEvent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
//
//import static com.sun.activation.registries.LogSupport.log;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/application.fxml"));
        primaryStage.setTitle("Go");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
        root.addEventFilter(KeyEvent.KEY_PRESSED, key  ->  {
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
//        try {
//            // The newInstance() call is a work around for some
//            // broken Java implementations
//
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection myConn = DriverManager.getConnection("JDBC:mysql://remotemysql.com:3306/dTXt3FVdSy",
//                    "dTXt3FVdSy", "s4dL5PTH35");
//
//        } catch (SQLException | ClassNotFoundException e) {
//            e.printStackTrace();
//            return;
//            // handle the error
//        }
//
//        Base.open();
//        Users u = new Users();
//        u.set("firstname", "Test");
//        u.set("lastname", "Tester");
//        u.set("username", "test01");
//        u.saveIt();
    }
}
