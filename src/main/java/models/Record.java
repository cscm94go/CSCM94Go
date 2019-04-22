package models;

import org.json.JSONObject;
/**
 * This is a game record
 * @author John
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
}
