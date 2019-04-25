package controllers;

import application.Main;
import helpers.HelperMethods;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import models.Game;
import models.Piece;
import models.Record;
import models.Users;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This is the controller to control the running of the game.
 * The game is multiple users game, a local file "game.json" is used to communicate.
 * So there is a class MyTask to handle the state sync. MyTask will runs every 500ms.
 */
public class GameController {

    @FXML Canvas canvas;
    @FXML Label opponentUserNameLabel;
    @FXML Label userNameLabel;
    @FXML Button finishBtn;
    @FXML Button passBtn;
    @FXML Button surrenderBtn;
    @FXML ImageView myImg;
    @FXML ImageView opponentImg;
    @FXML Label indicator;

    GraphicsContext gc;
    float width = 500;
    float left = 20;
    float top = 20;
    float r = 17;
    float unitWidth = width / 18;
    Game game = new Game();
    Timer timer;

    /**
     * Calculate whether a piece has qi
     */
    protected boolean hasQi(Piece piece, Piece pre, int[][] ints, boolean isWhite) {
        int x = piece.x;
        int y = piece.y;
        Piece piece1 = new Piece(x+1, y);
        Piece piece2 = new Piece(x-1, y);
        Piece piece3 = new Piece(x, y+1);
        Piece piece4 = new Piece(x, y-1);
        Piece pieces[] = new Piece[]{piece1, piece2, piece3, piece4};
        boolean res = false;
        for (int i = 0; i < pieces.length; i++) {
            Piece thePiece = pieces[i];
            if (thePiece.equals(pre)) continue;
            if (thePiece.x < 0 || thePiece.x > 18 || thePiece.y < 0 || thePiece.y > 18) continue;
            int value = ints[thePiece.x][thePiece.y];
            if (value == 0) {
                res = true;
            } else if (!isWhite && value == -1) {
                if (hasQi(thePiece, piece, ints, isWhite)) res = true;
            } else if (isWhite && value == 1) {
                if (hasQi(thePiece, piece, ints, isWhite)) res = true;
            }
        }
        return res;
    }

    /**
     * Task to be run sync data with local file
     */
    protected class MyTask extends TimerTask {
        @Override
        public void run() {
            try {
                String content = new String(Files.readAllBytes( Paths.get("game.json")), "UTF-8");
                JSONObject json = new JSONObject(content);
                // Check whether local file changed. If no, return.
                if (game.previousJSON != null && game.previousJSON.toString().equals(json.toString())) {
                    return;
                } else {
                    game.previousJSON = json;
                }
                // Check whether local file changed. If game over, hint user and return.
                boolean gameOver = json.getBoolean("gameOver");
                if (gameOver) {
                    Record record = new Record(json.getJSONObject("gameRecord"));
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (record.winner.equals(Users.currentUser.username)){
                                HelperMethods.showAlert(Alert.AlertType.CONFIRMATION, Main.stage, "Game over!",
                                        "you win");
                            } else {
                                HelperMethods.showAlert(Alert.AlertType.CONFIRMATION, Main.stage, "Game over!",
                                        "you lose");
                            }
                            try {
                                Parent p = FXMLLoader.load(getClass().getResource("/fxml/HomeDashboard.fxml"));
                                Scene board = new Scene(p, 1100, 900);
                                Main.stage.setScene(board);
                                Main.stage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    return;
                }
                // Handle the go pass rule. If two users both pass, game is over.
                if (json.has("someonePassed") && !json.getString("someonePassed").equals(Users.currentUser.username)) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            // Hint user
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Opponent passed, do you want pass, if so, game is over", ButtonType.YES, ButtonType.CANCEL);
                            alert.initOwner(Main.stage);
                            alert.showAndWait()
                                    .ifPresent(r -> {
                                        if (r == ButtonType.YES) {
                                            // Both pass, game over, create new record
                                            json.put("gameOver", true);
                                            Record record;
                                            if (game.meWin()) {
                                                record = new Record(
                                                        game.blackPlayerUserName,
                                                        game.whitePlayerUserName,
                                                        game.isWhitePlayer ? game.whitePlayerUserName: game.blackPlayerUserName
                                                );
                                            } else {
                                                record = new Record(
                                                        game.blackPlayerUserName,
                                                        game.whitePlayerUserName,
                                                        game.isWhitePlayer ? game.blackPlayerUserName: game.whitePlayerUserName
                                                );
                                            }

                                            try {
                                                json.put("gameRecord", record.toJSON());
                                                BufferedWriter writer = new BufferedWriter(new FileWriter("game.json"));
                                                writer.write(json.toString());
                                                writer.close();


                                                String playerRecords = new String(Files.readAllBytes( Paths.get("playerRecords.json")), "UTF-8");
                                                JSONArray playerRecordsJSON = new JSONArray(playerRecords);
                                                playerRecordsJSON.put(record.toJSON());
                                                BufferedWriter writer2 = new BufferedWriter(new FileWriter("playerRecords.json"));
                                                writer2.write(playerRecordsJSON.toString());
                                                writer2.close();

                                                HelperMethods.showAlert(
                                                        Alert.AlertType.CONFIRMATION,
                                                        Main.stage,
                                                        "Game over!",
                                                        game.meWin() ? "yo win": "you lose"
                                                );

                                                Parent p = FXMLLoader.load(getClass().getResource("/fxml/HomeDashboard.fxml"));
                                                Scene board = new Scene(p, 1100, 900);
                                                Main.stage.setScene(board);
                                                Main.stage.show();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                        } else {
                                            // Not both, game continue.
                                            json.remove("someonePassed");
                                            try {
                                                BufferedWriter writer = new BufferedWriter(new FileWriter("game.json"));
                                                writer.write(json.toString());
                                                writer.close();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                        }
                    });
                    return;
                }
                // Code below is for syncing state
                List<Object> ws = json.getJSONArray("whitePieces").toList();
                List<Object> bs = json.getJSONArray("blackPieces").toList();
                game.whitePieces = ws.stream().map(s -> {
                    int x = (int) ((HashMap) s).get("x");
                    int y = (int) ((HashMap) s).get("y");
                    return new Piece(x, y);
                }).collect(Collectors.toList());
                game.blackPieces = bs.stream().map(s -> {
                    int x = (int) ((HashMap) s).get("x");
                    int y = (int) ((HashMap) s).get("y");
                    return new Piece(x, y);
                }).collect(Collectors.toList());
                if (json.has("whitePlayer")) game.whitePlayerUserName = json.getString("whitePlayer");
                if (json.has("blackPlayer")) game.blackPlayerUserName = json.getString("blackPlayer");
                if (json.has("whitePlayerImage")) game.whitePlayerImage = json.getString("whitePlayerImage");
                if (json.has("blackPlayerImage")) game.blackPlayerImage = json.getString("blackPlayerImage");
                game.gameStart = json.getBoolean("gameStart");
                game.isWhite = json.getBoolean("isWhite");
                drawBroad();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Initializer
     */
    @FXML
    private void initialize() throws Exception{
        gc = canvas.getGraphicsContext2D();
        
        // add click event so that know where to put new piece
        canvas.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if (!game.gameStart || !game.meTurn()) return;
            Piece piece = calculatePiece(e.getX(), e.getY());
            if (piece == null) return;

            // Check the whether the position can put a piece
            boolean canPut = canPutPiece(game.whitePieces, game.blackPieces, piece, game.isWhite);
            if (!canPut) return;
            // Check hasQi for pieces to decide which pieces should be removed
            if (game.isWhite) {
                game.whitePieces.add(piece);
                List<Piece> is = new ArrayList<>();
                for (int i = 0; i < game.blackPieces.size(); i++) {
                    Piece p = game.blackPieces.get(i);
                    if (!hasQi(p, p, getMatrix(), false)) {
                        is.add(p);
                    }
                }
                for (int i = 0; i < is.size(); i++) {
                    game.blackPieces.remove(is.get(i));
                }
            } else {
                game.blackPieces.add(piece);
                List<Piece> is = new ArrayList<>();
                for (int i = 0; i < game.whitePieces.size(); i++) {
                    Piece p = game.whitePieces.get(i);
                    if (!hasQi(p, p, getMatrix(), true)) {
                        is.add(p);
                    }
                }
                for (int i = 0; i < is.size(); i++) {
                    game.whitePieces.remove(is.get(i));
                }
            }
            game.isWhite = !game.isWhite;
            drawBroad();
            updateFile();
        });

        // Response to user click finish button, go to home dashboard
        finishBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            try {
                Parent p = FXMLLoader.load(getClass().getResource("/fxml/HomeDashboard.fxml"));
                Scene board = new Scene(p, 1100, 900);
                Main.stage.setScene(board);
                Main.stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        // Response user click pass button, warn user
        passBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure pass", ButtonType.YES, ButtonType.CANCEL);
            alert.initOwner(Main.stage);
            alert.showAndWait()
                    .filter(response -> response == ButtonType.YES)
                    .ifPresent(r -> {
                        try {
                            String content = new String(Files.readAllBytes( Paths.get("game.json")), "UTF-8");
                            JSONObject json = new JSONObject(content);
                            json.put("someonePassed", Users.currentUser.username);
                            BufferedWriter writer = new BufferedWriter(new FileWriter("game.json"));
                            writer.write(json.toString());
                            writer.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    });
        });
        
        // Response user click surrender button, warn user, 
        surrenderBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure surrender", ButtonType.YES, ButtonType.CANCEL);
            alert.initOwner(Main.stage);
            alert.showAndWait()
                    .filter(response -> response == ButtonType.YES)
                    .ifPresent(r -> {
                        // Handle user click yes button, then game over, create new game record
                        HelperMethods.showAlert(Alert.AlertType.CONFIRMATION, Main.stage, "Game over!",
                                "you surrender");
                        try {
                            Parent p = FXMLLoader.load(getClass().getResource("/fxml/HomeDashboard.fxml"));
                            Scene board = new Scene(p, 1100, 900);
                            Main.stage.setScene(board);
                            Main.stage.show();

                            String content = new String(Files.readAllBytes( Paths.get("game.json")), "UTF-8");
                            JSONObject json = new JSONObject(content);
                            json.put("gameOver", true);
                            Record record = new Record(
                                    game.blackPlayerUserName,
                                    game.whitePlayerUserName,
                                    game.isWhitePlayer ? game.blackPlayerUserName: game.whitePlayerUserName
                            );
                            json.put("gameRecord", record.toJSON());
                            BufferedWriter writer = new BufferedWriter(new FileWriter("game.json"));
                            writer.write(json.toString());
                            writer.close();

                            String playerRecords = new String(Files.readAllBytes( Paths.get("playerRecords.json")), "UTF-8");
                            JSONArray playerRecordsJSON = new JSONArray(playerRecords);
                            playerRecordsJSON.put(record.toJSON());
                            BufferedWriter writer2 = new BufferedWriter(new FileWriter("playerRecords.json"));
                            writer2.write(playerRecordsJSON.toString());
                            writer2.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    });
        });

        // First draw board and update ui
        drawBroad();
        
        // Create a timer to sync the local game file every 500 ms.
        timer = new Timer(true);
        timer.schedule(new MyTask(), 500, 1000);
    }

    /**
     * OverWrite local game file with current game state
     */
    private void updateFile(){
        try {
            String content = new String(Files.readAllBytes( Paths.get("game.json")), "UTF-8");
            JSONObject json = new JSONObject(content);
            json.put("whitePieces", new JSONArray(game.whitePieces.stream().map(e -> e.toJSON()).toArray()));
            json.put("blackPieces", new JSONArray(game.blackPieces.stream().map(e -> e.toJSON()).toArray()));
            json.put("isWhite", game.isWhite);
            BufferedWriter writer = new BufferedWriter(new FileWriter("game" + ".json"));
            writer.write(json.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Use the cordinate x, y to calculate the Piece postion.
     */
    private Piece calculatePiece(double x, double  y){
        double  minX = left - unitWidth / 2;
        double  maxX = left + width + unitWidth / 2;
        double  minY = top - unitWidth / 2;
        double  maxY = top + width + unitWidth / 2;
        if (x < minX || x > maxX || y < minY || y > maxY) return null;

        int a = (int) Math.round((x - left) / unitWidth);
        int b = (int) Math.round((y - top) / unitWidth);
        return new Piece(a,b);
    }
    /**
     * Check whether user is able to put piece on certain  position
     */
    private boolean canPutPiece(List<Piece> whitePieces, List<Piece> blackPieces, Piece newPiece, boolean isWhite){
        List<Piece> selfPieces = isWhite ? whitePieces: blackPieces;
        List<Piece> opponentPieces = isWhite ? blackPieces: whitePieces;
        if (selfPieces.contains(newPiece) || opponentPieces.contains(newPiece)) return false;
        if (!hasQi(newPiece, newPiece, getMatrix(), isWhite) ) return false;
        return true;
    }
    /**
     * Drawing board and update ui with current game state.
     */
    private void drawBroad(){
        // Draw game board use canvas
        gc.clearRect(0, 0, width, width);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.setFill(Color.GRAY);
        gc.fillRect(2,2, width + 36, width + 36);
        for (int i = 0; i < 18; i++)
            for (int j = 0; j < 18; j++)
                gc.strokeRect(left + i * unitWidth,top + j * unitWidth, unitWidth, unitWidth);
        float l = left - r / 2;
        float b = top - r / 2;
        gc.setFill(Color.BLACK);
        game.blackPieces.forEach(piece -> gc.fillOval( l + piece.x * unitWidth, b + piece.y * unitWidth, r, r));
        gc.setFill(Color.WHITE);
        game.whitePieces.forEach(piece -> gc.fillOval( l + piece.x * unitWidth, b + piece.y * unitWidth, r, r));

        // Update ui
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (game.meTurn() && game.gameStart) {
                    indicator.setStyle("-fx-background-color: green");
                } else {
                    indicator.setStyle("-fx-background-color: red");
                }
                opponentUserNameLabel.setText(game.isWhitePlayer ? game.blackPlayerUserName: game.whitePlayerUserName);
                userNameLabel.setText(game.isWhitePlayer ? game.whitePlayerUserName : game.blackPlayerUserName);

                myImg.setImage(new Image(Users.currentUser.image));
                String opponentImagePath = game.isWhitePlayer ? game.blackPlayerImage: game.whitePlayerImage;
                if (opponentImagePath != null) {
                    opponentImg.setImage(new Image(opponentImagePath));
                }
            }
        });
    }
    /**
     * Get a 19x19 to represent a board with blackPieces List and whitePieces List
     */
    private int[][] getMatrix() {
        int[][] ints = new int[19][19];

        for (int i = 0; i < game.blackPieces.size(); i++) {
            Piece p = game.blackPieces.get(i);
            ints[p.x][p.y] = -1;
        }
        for (int i = 0; i < game.whitePieces.size(); i++) {
            Piece p = game.whitePieces.get(i);
            ints[p.x][p.y] = 1;
        }
        return ints;
    }
}

