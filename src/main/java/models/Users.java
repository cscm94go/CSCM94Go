package models;

import org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


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
    /**
     * User's avatar file path
     */
    public String image;
    /**
     * User's last login timestamp
     */
    public long lastLogin;
    /**
     * User's registered timestamp
     */
    public long registerTime;
    /**
     * User's lead board position
     */
    public long position;


    /**
     * This gives the information of the user.
     * @return Return the user data.
     */
    private String toJson() {
        JSONObject json = new JSONObject();
        json.put("firstname",firstname);
        json.put("lastname",lastname);
        json.put("username",username);
        json.put("image",image);
        json.put("lastLogin",lastLogin);
        json.put("registerTime",registerTime);
        json.put("position",position);
        return json.toString();
    }

    /**
     * This initializes user from a json string.
     * @param jsonString Takes the user information.
     */
    public Users(String jsonString) {
        JSONObject json = new JSONObject(jsonString);
        firstname = (String) json.get("firstname");
        lastname = (String) json.get("lastname");
        username = (String) json.get("username");
        image = (String) json.get("image");
        lastLogin =  json.getLong("lastLogin");
        registerTime =  json.getLong("registerTime");
        position =  json.getLong("position");
    }

    /**
     * Default Users class constructor.
     */
    public Users() {}

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
}
