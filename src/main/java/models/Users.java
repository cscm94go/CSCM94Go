package models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
import org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Table("users")

public class Users {

    public String firstname;
    public String lastname;
    public String username;
    public String image;
    public long lastLogin;
    public long registerTime;
    public long position;

    public static Users currentUser;

    public String toJson() {
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

    public Users() {}

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
