package models;

import org.json.JSONObject;

public class Record {

    public String player1;
    public String player2;
    public String winner;

    public Record(JSONObject json){
        winner = json.getString("winner");
        player1 = json.getString("player1");
        player2 = json.getString("player2");
    }
}
