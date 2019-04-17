package application;

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
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/application.fxml"));
        primaryStage.setTitle("Go");
        primaryStage.setScene(new Scene(root, 300, 275));
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
