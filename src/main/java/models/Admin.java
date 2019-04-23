package models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents a user with administrator privileges.
 * @author Hector
 * @version 1.1
 */
public class Admin extends Users {
    /**
     * This attribute denotes that the user is administrator.
     */
    private static final boolean isAdminRole = true;


    public JSONArray usersList = new JSONArray();

    public String [] simpleUsersList = new String[100];


    /**
     * This method is the constructor.
     *
     * @param user
     */
    public Admin(Users user) {
        super(user.toJson());
    }

    public Admin() {

    }

    /**
     * This method checks the admintrator's privileges.
     *
     * @param admin The administrator.
     * @return true if the the user is administrator.
     */
    public boolean Role(Admin admin) {
        return admin.isAdminRole;
    }
    /**
     * This method creates a list of users.
     */
    public static List<Users> getUsersList() {
        List<Users> us = new ArrayList<>();
        try {
            us = Files.list(new File("users").toPath())
                    .map(p -> {
                        Users u = new Users();
                        try {
                            u = new Users(new String(Files.readAllBytes(p), "UTF-8"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return u;
                    }).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return us;
    }
}