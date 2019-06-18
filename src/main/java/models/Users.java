package models;

import javafx.scene.control.CheckBox;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * This class represent a user.
 * @author Zakariye Ali
 */
public class Users {

    /**
     * This holds the logged user.
     */
    public static Users currentUser;

    /**
     * User's first name
     */
    public String firstname;
    /**
     * User's last name
     */
    public String lastname;
    /**
     * User's user name
     */
    public String username;


    public Users() {

    }





    /**
     * This gives the information of the user.
     * @return Return the user data.
     */
    public String toJson() {
        JSONObject json = new JSONObject();
        json.put("firstname",firstname);
        json.put("lastname",lastname);
        json.put("username",username);
        return json.toString();
    }

    /**
     * This initializes user from a json string.
     * @param jsonString Takes the user information.
     */
    //Users
    public Users(String jsonString) {
        JSONObject json = new JSONObject(jsonString);
        firstname = (String) json.get("firstname");
        lastname = (String) json.get("lastname");
        username = (String) json.get("username");
    }

    /**
     * Default Users class constructor.
     */
    public Users(String firstname, String lastname, String username, String image, long lastLogin, long registerTime, long position) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
    }

    /**
     * Save user to local file
     */
    public void store()  {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("users/" + username + ".json"));
            writer.write(toJson());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONArray usersList = new JSONArray();

    public Object [] simpleUsersList = new Object[100];


}
