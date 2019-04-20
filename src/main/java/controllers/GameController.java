package controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    @FXML public Canvas canvas;
    public GraphicsContext gc;
    public float width = 500;
    float left = 20;
    float top = 20;
    float r = 17;
    float unitWidth = width / 18;

    List<Piece> blackPieces = new ArrayList<>();
    List<Piece> whitePieces = new ArrayList<>();

    boolean isWhite = true;

    @FXML
    public void initialize() throws Exception{
        gc = canvas.getGraphicsContext2D();


        //Registering the event filter
        canvas.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            Piece piece = calculatePiece(e.getX(), e.getY());
            if (piece == null) return;

            boolean canPut = canPutPiece(whitePieces, blackPieces, piece, isWhite);
            if (!canPut) return;

            if (isWhite) whitePieces.add(piece);
            else blackPieces.add(piece);
            drawBroad();
            isWhite = !isWhite;
        });

        drawBroad();
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

        opponentPieces

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
        blackPieces.forEach(piece -> gc.fillOval( l + piece.x * unitWidth, b + piece.y * unitWidth, r, r));
        gc.setFill(Color.WHITE);
        whitePieces.forEach(piece -> gc.fillOval( l + piece.x * unitWidth, b + piece.y * unitWidth, r, r));
    }

}

class Piece {
    public int x;
    public int y;

    public Piece(int i, int i1) {
        x=i;y=i1;
    }

    @Override
    public boolean equals(Object obj) {
        return x==((Piece)obj).x && y==((Piece)obj).y;
    }
}

