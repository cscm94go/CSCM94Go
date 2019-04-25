package models;

import javafx.scene.control.CheckBox;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static controllers.LeadBoardController.sort;


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

    private CheckBox makeAdmin;

    public Users() {

    }

    /**
     * Get wins and losses for the currently logged in user
     */
    public static int[] getCurrentUserWinsAndLosesCount(){
        int[] winsAndLoses = new int[2];
        if (Files.exists(Paths.get("playerRecords.json"))) {
            try {
                String content = new String(Files.readAllBytes( Paths.get("playerRecords.json")), "UTF-8");
                JSONArray jsonArray = new JSONArray(content);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = (JSONObject) jsonArray.get(i);
                    Record r = new Record(json);
                    if (r.player1.equals(currentUser.username) || r.player2.equals(currentUser.username)){
                        if (r.winner.equals(currentUser.username)) {
                            winsAndLoses[0] ++;
                        }else {
                            winsAndLoses[1] ++;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return winsAndLoses;
    }

    /**
     * Get win percentage for currently logged in player
     */
    public static double getCurrentUserWinPercentage() {
        double winPercentage = 0;
        if (Files.exists(Paths.get("playerRecords.json"))) {
            try {
                String content = new String(Files.readAllBytes(Paths.get("playerRecords.json")), "UTF-8");
                JSONArray json = new JSONArray(content);
                List<Record> rs = new ArrayList<Record>();
                json.forEach(e -> {
                    JSONObject j = (JSONObject) e;
                    Record r = new Record(j);
                    rs.add(r);
                });
                HashSet<String> users = new HashSet<>();
                rs.stream().forEach(r -> {
                    users.add(r.player1);
                    users.add(r.player2);
                });
                String u = currentUser.username;
                AtomicInteger win = new AtomicInteger();
                AtomicInteger lose = new AtomicInteger();
                rs.stream().forEach(r -> {
                  if (r.player1.equals(u) || r.player2.equals(u)) {
                    if (r.winner.equals(u)) win.getAndIncrement();
                      else lose.getAndIncrement();
                  }
                });
                winPercentage = win.doubleValue() / (win.doubleValue() + lose.doubleValue());
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return winPercentage * 100;
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
    //Users
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
    public Users(String firstname, String lastname, String username, String image, long lastLogin, long registerTime, long position) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.image = image;
        this.lastLogin = lastLogin;
        this.registerTime = registerTime;
        this.position = position;
        this.makeAdmin = new CheckBox();
    }

    public CheckBox getMakeAdmin() {
        return makeAdmin;
    }

    public void setMakeAdmin(CheckBox makeAdmin) {
        this.makeAdmin = makeAdmin;
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
