package application;

import controllers.AccountController;
import models.Users;
import org.javalite.activejdbc.Base;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//
//import static com.sun.activation.registries.LogSupport.log;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader firstPaneLoader = new FXMLLoader(getClass().getResource("/fxml/application.fxml"));
        Parent firstPane = firstPaneLoader.load();
        Scene firstScene = new Scene(firstPane, 1100, 900);


        //get loader and pane for 2nd scene
        FXMLLoader secondPageLoader = new FXMLLoader(getClass().getResource("/fxml/registerUser.fxml"));
        Parent secondPane = secondPageLoader.load();
        Scene secondScene = new Scene(secondPane, 1100,900);

        //inject scenes in to controller
        AccountController firstPaneController = (AccountController) firstPaneLoader.getController();
        AccountController secondPaneController = (AccountController) secondPageLoader.getController();
        firstPaneController.setSecondScene(secondScene);
        secondPaneController.setFirstScene(firstScene);

        primaryStage.setTitle("Go");
        primaryStage.setScene(firstScene);
        primaryStage.show();
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
