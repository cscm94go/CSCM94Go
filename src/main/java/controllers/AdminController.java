package controllers;
import javafx.beans.property.SimpleObjectProperty;
import models.Admin;
import models.Users;
import java.util.Date;

public class AdminController {


    String userName =  "a";


    SimpleObjectProperty<Date> joinDate = new SimpleObjectProperty<>(this, "joinDate", new Date());


    int adminNum = 1;

    Admin admin = new Admin(userName, joinDate.get(), adminNum);


    public void showAdminDetails(String adminName){

        System.out.println("Name: " + adminName);

    }


}
