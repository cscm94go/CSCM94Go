package models;

import org.json.JSONObject;
/**
 * This represent a piece (stone) on board
 * @author John Li.
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
     * @param _x initial position on the board.
     * @param _y initial position on the board.
     */
    public Piece(int _x, int _y) {
        x=_x;y=_y;
    }
    /**
     * Initial position from json string
     * @param jsonString json object as string
     * with the coordinates where the piece is set initally.
     */
    public Piece(String jsonString){
        JSONObject json = new JSONObject(jsonString);
        x = json.getInt("x");
        y = json.getInt("y");
    }
    /**
     * Convert to json object the actual position,
     * described by two coordinates (x, y).
     * @return the actual position.
     */
    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("x", x);
        json.put("y", y);
        return json;
    }
    @Override
    /**
     * @return Convert to string the actual position.
     */
    public String toString() {
        return toJSON().toString();
    }
    @Override
    /**
     * @return If two pieces are in the same position.
     */
    public boolean equals(Object obj) {
        return x==((Piece)obj).x && y==((Piece)obj).y;
    }
}
