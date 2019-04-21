package models;

import org.json.JSONObject;

public class Piece {
    public int x;
    public int y;

    public Piece(int i, int i1) {
        x=i;y=i1;
    }

    @Override
    public String toString() {
        return toJSON().toString();
    }

    public JSONObject toJSON(){
        JSONObject json = new JSONObject();
        json.put("x", x);
        json.put("y", y);
        return json;
    }

    public Piece(String jsonString){
        JSONObject json = new JSONObject(jsonString);
        x = json.getInt("x");
        y = json.getInt("y");
    }

    @Override
    public boolean equals(Object obj) {
        return x==((Piece)obj).x && y==((Piece)obj).y;
    }
}
