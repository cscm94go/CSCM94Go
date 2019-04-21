package models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Game {
    public List<Piece> blackPieces = new ArrayList<>();
    public List<Piece> whitePieces = new ArrayList<>();

    public boolean isWhite;
    public boolean gameStart;

    public String blackPlayerUserName;
    public String whitePlayerUserName;

    public boolean isWhitePlayer;

    public boolean meTurn(){
        return (isWhite && isWhitePlayer) || (!isWhite && !isWhitePlayer);
    }

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
