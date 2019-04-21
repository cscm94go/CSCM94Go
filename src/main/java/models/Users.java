package models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
import org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


/**
 * This class represent user
 */
public class Users {

    /**
     * This static variable holds login user data
     */
    public static Users currentUser;

    /**
     * user's first name
     */
    public String firstname;
    /**
     * user's last name
     */
    public String lastname;
    /**
     * user's user name
     */
    public String username;
    /**
     * user's avatar file path
     */
    public String image;
    /**
     * user's last login timestamp
     */
    public long lastLogin;
    /**
     * user's registered timestamp
     */
    public long registerTime;
    /**
     * user's lead board position
     */
    public long position;

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
     * Init user from json string
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
     * Default user initializer
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
