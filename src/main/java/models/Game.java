package models;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
/**
 * This class holds game data
 * @author John Li
 */
public class Game {
    /**
     * Black pieces list
     */
    public List<Piece> blackPieces = new ArrayList<>();
    /**
     * White pieces list
     */
    public List<Piece> whitePieces = new ArrayList<>();
    /**
     * Indicate who is next to put piece
     */
    public boolean isWhite;
    /**
     * Indicate whether game start
     */
    public boolean gameStart;
    /**
     * Username of black player
     */
    public String blackPlayerUserName;
    /**
     * Username of white player
     */
    public String whitePlayerUserName;
    /**
     * Indicate whether current user is white player
     */
    /**
     * Avatar path of black player
     */
    public String blackPlayerImage;
    /**
     * Avatar path of white player
     */
    public String whitePlayerImage;
    /**
     * Indicate whether current user is white player
     */
    public boolean isWhitePlayer;
    /**
     * Use to compare with local file
     */
    public JSONObject previousJSON;
    /**
     * Check is it my turn, if not cant put piece
     * @return whether is my turn to put piece
     */
    public boolean meTurn(){
        return (isWhite && isWhitePlayer) || (!isWhite && !isWhitePlayer);
    }
    /**
     * Use when game over, check whether I win, draw, or lose, 1 - win, 0 - draw, - lose
     * @return whether I win or not. 1 - win, 0 - draw, - lose
     */
    public boolean meWin(){
        return whitePieces.size() > blackPieces.size() && isWhitePlayer;
    }

    /**
     * Default init
     */
    public Game(){
        try {
            JSONObject json;
            if (Files.exists(Paths.get("game.json"))) {
                String content = new String(Files.readAllBytes( Paths.get("game.json")), "UTF-8");
                json = new JSONObject(content);
                json = json.has("blackPlayer") || json.getBoolean("gameOver") ? new JSONObject() : json;
            }else {
                json = new JSONObject();
            }

            if (!json.has("whitePlayer")) {
                json.put("whitePieces", new ArrayList<>());
                json.put("blackPieces", new ArrayList<>());
                json.put("whitePlayer", Users.currentUser.username);
                json.put("gameStart", false);
                json.put("isWhite", true);
                json.put("gameOver", false);
                json.put("whitePlayerImage", Users.currentUser.image);
                isWhitePlayer = true;
            } else {
                json.put("blackPlayer", Users.currentUser.username);
                json.put("gameStart", true);
                json.put("blackPlayerImage", Users.currentUser.image);
                isWhitePlayer = false;
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter("game.json"));
            writer.write(json.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
