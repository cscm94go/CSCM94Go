package models;

import org.json.JSONObject;
/**
 * This represent a piece (stone) on board
 */
public class Piece {
    /**
     * x coordinate of the piece
     */
    public int x;
    /**
     * y coordinate of the piece
     */
    public int y;

    /**
     * Init from two ints
     */
    public Piece(int _x, int _y) {
        x=_x;y=_y;
    }
    /**
     * Init from json string
     */
    public Piece(String jsonString){
        JSONObject json = new JSONObject(jsonString);
        x = json.getInt("x");
        y = json.getInt("y");
    }
    /**
     * Convert to json object
     */
    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("x", x);
        json.put("y", y);
        return json;
    }
    @Override
    /**
     * convert to string
     */
    public String toString() {
        return toJSON().toString();
    }
    @Override
    /**
     * overwrite equals
     */
    public boolean equals(Object obj) {
        return x==((Piece)obj).x && y==((Piece)obj).y;
    }
}
