package models;

import org.json.JSONObject;

import java.util.Date;

/**
 * This is a game record
 * @author John Li
 */
public class Record {
    /**
     * This is the username of the first player
     */
    public String player1;
    /**
     * This is the username of the second player
     */
    public String player2;
    /**
     * This is the username of the winner
     */
    public String winner;
    /**
     * This is the timestamp when the game ends
     */
    public long timeStamp;
    /**
     * Initialize from a json object
     * @param json Information with the players names
     * and the ending timestamp.
     */
    public Record(JSONObject json){
        winner = json.getString("winner");
        player1 = json.getString("player1");
        player2 = json.getString("player2");
        timeStamp = json.getLong("timeStamp");
    }
    /**
     * Initialize from a game data
     * @param _player1 player1 username
     * @param _player2 player2 username
     * @param _winner winner username
     */
    public Record(String _player1, String _player2, String _winner){
        player1=_player1;
        player2=_player2;
        winner=_winner;
        timeStamp=new Date().getTime();
    }
    /**
     * Convert record to be a json object
     * @return a json object
     */
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("player1", player1);
        json.put("player2", player2);
        json.put("winner", winner);
        json.put("timeStamp", timeStamp);
        return json;
    }
}
