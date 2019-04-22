package models;

import org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
/**
 * This class holds the game's logic data,
 * who is next to put a piece.
 * @author John
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
    public boolean isWhitePlayer;
    /**
     * Check is it my turn, if not, I cannot put piece.
     * @return True if it is next to put piece.
     */
    public boolean meTurn(){
        return (isWhite && isWhitePlayer) || (!isWhite && !isWhitePlayer);
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
                json = json.has("blackPlayer") ? new JSONObject() : json;
            }else {
                json = new JSONObject();
            }

            if (!json.has("whitePlayer")) {
                json.put("whitePieces", new ArrayList<>());
                json.put("blackPieces", new ArrayList<>());
                json.put("whitePlayer", Users.currentUser.username);
                json.put("gameStart", false);
                json.put("isWhite", true);
                isWhitePlayer = true;
            } else {
                json.put("blackPlayer", Users.currentUser.username);
                json.put("gameStart", true);
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
