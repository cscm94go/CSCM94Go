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
import javafx.scene.control.ButtonType;
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
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

/**
 * This is the controller to control the running of the game
 */
public class GameController {

    @FXML Canvas canvas;
    @FXML Label opponentUserNameLabel;
    @FXML Label userNameLabel;
    @FXML Label whoseTurn;
    @FXML Button finishBtn;
    @FXML Button passBtn;
    @FXML Button surrenderBtn;
    @FXML ImageView myImg;
    @FXML ImageView opponentImg;

    

    GraphicsContext gc;
    float width = 500;
    float left = 20;
    float top = 20;
    float r = 17;
    float unitWidth = width / 18;
    Game game = new Game();
    Timer timer;

    @FXML
    private void initialize() throws Exception{
        gc = canvas.getGraphicsContext2D();
        //Registering the event filter
        canvas.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if (!game.gameStart || !game.meTurn()) return;
            Piece piece = calculatePiece(e.getX(), e.getY());
            if (piece == null) return;

            boolean canPut = canPutPiece(game.whitePieces, game.blackPieces, piece, game.isWhite);
            if (!canPut) return;

            if (game.isWhite) game.whitePieces.add(piece);
            else game.blackPieces.add(piece);
            game.isWhite = !game.isWhite;
            drawBroad();
            updateFile();
        });

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
        
        surrenderBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure surrender", ButtonType.YES, ButtonType.CANCEL);
            alert.initOwner(Main.stage);
            alert.showAndWait()
                    .filter(response -> response == ButtonType.YES)
                    .ifPresent(r -> {
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
                                    game.isWhite ? game.blackPlayerUserName: game.whitePlayerUserName
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

        drawBroad();

        timer = new Timer(true);
        timer.schedule(new MyTask(), 500,1000);
    }

    private class MyTask extends TimerTask {
        @Override
        public void run() {

            try {
                String content = new String(Files.readAllBytes( Paths.get("game.json")), "UTF-8");
                JSONObject json = new JSONObject(content);
                if (game.previousJSON != null && game.previousJSON.toString().equals(json.toString())) {
                    return;
                } else {
                    game.previousJSON = json;
                }
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
                if (json.has("someonePassed") && !json.getString("someonePassed").equals(Users.currentUser.username)) {

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Opponent passed, do you want pass, if so, game is over", ButtonType.YES, ButtonType.CANCEL);
                            alert.initOwner(Main.stage);
                            alert.showAndWait()
                                    .ifPresent(r -> {
                                        if (r == ButtonType.YES) {
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

    private boolean canPutPiece(List<Piece> whitePieces, List<Piece> blackPieces, Piece newPiece, boolean isWhite){
        List<Piece> selfPieces = isWhite ? whitePieces: blackPieces;
        List<Piece> opponentPieces = isWhite ? blackPieces: whitePieces;
        if (selfPieces.contains(newPiece) || opponentPieces.contains(newPiece)) return false;
        return true;
    }
    private List <Piece>[] adjustPiece(List<Piece> whitePieces, List<Piece> blackPieces, Piece newPiece, boolean isWhite){
        List<Piece> selfPieces = isWhite ? whitePieces: blackPieces;
        List<Piece> opponentPieces = isWhite ? blackPieces: whitePieces;

//        opponentPieces
        return null;
    }


    private void drawBroad(){
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

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                whoseTurn.setText(game.isWhite ? "White" : "Black");
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
}

