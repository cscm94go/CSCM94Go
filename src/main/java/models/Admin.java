package models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
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
    public static Admin currentAdmin;

    private final boolean isAdminRole = true;

    private int adminNumber;

    public JSONArray usersList = new JSONArray();

    public String [] simpleUsersList = new String[100];


    /**
     * This method is the constructor.
     *
     * @param user object
     */
    public Admin(Users user) {
        super(user.toJson());
    }
    public Admin(String jsonString) {
        JSONObject json = new JSONObject(jsonString);
            firstname = (String) json.get("firstname");
            lastname = (String) json.get("lastname");
            username = (String) json.get("username");
            image = (String) json.get("image");
            lastLogin =  json.getLong("lastLogin");
            registerTime =  json.getLong("registerTime");
            position =  json.getLong("position");
        adminNumber = json.getInt("adminNumber");
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
     * This method set the admintrator's number.
     * @param adminNumber The administrator number.
     */
    public void setAdminNumber(int adminNumber) {
        this.adminNumber = adminNumber;
    }
    /**
     * This method gets the admintrator's number.
     * @return the administrator number.
     */
    public int getAdminNumber(){
        return adminNumber;
    }
    /**
     * This method creates a list of users.
     * @return the all users.
     */
    public static List<Users> getUsersList() {
        List<Users> us = new ArrayList<>();
        try {
            us = Files.list(new File("users").toPath())
                    .map(p -> {
                        Users u = new Users();
                        try {
                            String jsonString = new String(Files.readAllBytes(p));
                            JSONObject json = new JSONObject(jsonString);
                            if (json.has("isAdmin")) {
                                u = new Admin(new String(Files.readAllBytes(p), "UTF-8"));
                            } else {
                                u = new Users(new String(Files.readAllBytes(p), "UTF-8"));
                            }
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