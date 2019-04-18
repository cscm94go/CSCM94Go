package controllers;
import javafx.beans.property.SimpleObjectProperty;
import models.Admin;
import models.Users;
import org.javalite.activejdbc.Model;

import java.awt.*;
import java.util.Date;
import java.sql.*;


public class AdminController {

    Users user = Users.findFirst("username = John");


    String userName =  u.getUserName();


    SimpleObjectProperty<Date> joinDate = new SimpleObjectProperty<>(this, "joinDate", new Date());


    int adminNum = 1;

    Admin admin = new Admin(userName, joinDate.get(), adminNum);


    public void showAdminDetails(String adminName){

        System.out.println("Name: " + adminName);

    }










}
