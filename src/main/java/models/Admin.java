package models;

import com.mysql.cj.xdevapi.JsonArray;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.sql.Array;
import java.util.Date;
import java.util.List;

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
    public void getUsersList() {

        File folder = new File("/users/");

        File[] listOfFiles = folder.listFiles();

        int counter =0;
        for (
                File file : listOfFiles) {
            if (file.isFile()) {

                String s = file.getName();

                JSONObject object = new JSONObject(s);

                String firstName = (String) object.get("firstName");

                simpleUsersList[counter] = firstName; counter++;

                usersList.put(object);
            }
        }
    }
}