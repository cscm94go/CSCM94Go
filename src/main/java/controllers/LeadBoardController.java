package controllers;

import application.Main;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Record;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Short one line description.
 * @author
 * @version
 *
 */
public class LeadBoardController {

    @FXML
    Button btn1;
    @FXML
    Button btn2;
    @FXML
    Button back;
//    @FXML
//    Button leaderBoard;
    @FXML TableView table;

    @FXML
    public void initialize() throws Exception{
        firstSort();

        btn1.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, a -> {
            try {
                firstSort();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        btn2.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, a -> {
            try {
                secondSort();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        back.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, a -> {

                try {
                    Parent p = FXMLLoader.load(getClass().getResource("/fxml/HomeDashboard.fxml"));
                    Scene board = new Scene(p, 1100, 900);
                    Main.stage.setScene(board);
                    Main.stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        });

    }

    public static List<String> sort(int type){
        String content = null;
        try {
            content = new String(Files.readAllBytes( Paths.get("playerRecords.json")), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray json = new JSONArray(content);

        List<Record> rs = new ArrayList<Record>();
        json.forEach(e -> {
            JSONObject j = (JSONObject) e;
            Record r = new Record(j);
            rs.add(r);
        });

        HashSet<String> users = new HashSet<>();
        rs.stream().forEach(r -> {
            users.add(r.player1);
            users.add(r.player2);
        });

        List<String> us = users.stream().collect(Collectors.toList());
        us.sort((u1, u2) -> {
            AtomicInteger win = new AtomicInteger();
            AtomicInteger lose = new AtomicInteger();
            AtomicInteger win2 = new AtomicInteger();
            AtomicInteger lose2 = new AtomicInteger();
            rs.stream().forEach(r -> {
                if (r.player1.equals(u1) || r.player2.equals(u1)) {
                    if (r.winner.equals(u1)) win.getAndIncrement();
                    else lose.getAndIncrement();
                }
                if (r.player1.equals(u2) || r.player2.equals(u2)) {
                    if (r.winner.equals(u2)) win2.getAndIncrement();
                    else lose2.getAndIncrement();
                }
            });
            double percentage1 = win.doubleValue() / (win.doubleValue() + lose.doubleValue());
            double percentage2 = win2.doubleValue() / (win2.doubleValue() + lose2.doubleValue());
            if (type == 0) return percentage1 < percentage2 ? 1 : -1;
            else return win.get() < win2.get() ? 1: -1;
        });
        return us;
    }

    void firstSort() throws IOException {
        table.getColumns().remove(0, table.getColumns().size());
        table.getItems().remove(0, table.getItems().size());
        TableColumn<WinPercentage, String> column1 = new TableColumn<>("Rank");
        column1.setPrefWidth(100);
        column1.setCellValueFactory(cell -> {
            return new ReadOnlyStringWrapper(cell.getValue().rank.toString());
        });
        TableColumn<WinPercentage, String> column2 = new TableColumn<>("Win percentage");
        column2.setPrefWidth(150);
        column2.setCellValueFactory(cell -> {
            Double a = cell.getValue().percentage * 100;
            DecimalFormat df = new DecimalFormat("0.0");

            return new ReadOnlyStringWrapper(df.format(a) + "%");
        });
        TableColumn<WinPercentage, String> column3 = new TableColumn<>("Name");
        column3.setPrefWidth(250);
        column3.setCellValueFactory(cell -> {
            return new ReadOnlyStringWrapper(cell.getValue().name);
        });
        table.getColumns().add(column1);
        table.getColumns().add(column2);
        table.getColumns().add(column3);

        String content = new String(Files.readAllBytes( Paths.get("playerRecords.json")), "UTF-8");
        JSONArray json = new JSONArray(content);

        List<Record> rs = new ArrayList<Record>();
        json.forEach(e -> {
            JSONObject j = (JSONObject) e;
            Record r = new Record(j);
            rs.add(r);
        });

        HashSet<String> users = new HashSet<>();
        rs.stream().forEach(r -> {
            users.add(r.player1);
            users.add(r.player2);
        });

        List<String> us = sort(0);
        for (int i = 0; i < us.size(); i++) {
            String u = us.get(i);
            AtomicInteger win = new AtomicInteger();
            AtomicInteger lose = new AtomicInteger();
            rs.stream().forEach(r -> {
                if (r.player1.equals(u) || r.player2.equals(u)) {
                    if (r.winner.equals(u)) win.getAndIncrement();
                    else lose.getAndIncrement();
                }
            });
            double percentage = win.doubleValue() / (win.doubleValue() + lose.doubleValue());

            table.getItems().add(new WinPercentage(percentage, i + 1, u));
        }
    }

    private class WinPercentage {
        Double percentage;
        Integer rank;
        String name;
        public WinPercentage(double p, int r, String n){
            percentage=new Double(p);
            rank=new Integer(r);
            name=n;
        }
    }

    private class TotalWin {
        Integer winCount;
        Integer rank;
        String name;
        public TotalWin(int c, int r, String n){
            winCount=new Integer(c);
            rank=new Integer(r);
            name=n;
        }
    }


    void secondSort() throws IOException {
        table.getColumns().remove(0, table.getColumns().size());
        table.getItems().remove(0, table.getItems().size());
        TableColumn<TotalWin, String> column1 = new TableColumn<>("Rank");
        column1.setPrefWidth(100);
        column1.setCellValueFactory(cell -> {
            return new ReadOnlyStringWrapper(cell.getValue().rank.toString());
        });
        TableColumn<TotalWin, String> column2 = new TableColumn<>("Total win");
        column2.setPrefWidth(100);
        column2.setCellValueFactory(cell -> {
            return new ReadOnlyStringWrapper(cell.getValue().winCount.toString());
        });
        TableColumn<TotalWin, String> column3 = new TableColumn<>("Name");
        column3.setPrefWidth(300);
        column3.setCellValueFactory(cell -> {
            return new ReadOnlyStringWrapper(cell.getValue().name);
        });
        table.getColumns().add(column1);
        table.getColumns().add(column2);
        table.getColumns().add(column3);

        String content = new String(Files.readAllBytes( Paths.get("playerRecords.json")), "UTF-8");
        JSONArray json = new JSONArray(content);

        List<Record> rs = new ArrayList<Record>();
        json.forEach(e -> {
            JSONObject j = (JSONObject) e;
            Record r = new Record(j);
            rs.add(r);
        });

        HashSet<String> users = new HashSet<>();
        rs.stream().forEach(r -> {
            users.add(r.player1);
            users.add(r.player2);
        });

        List<String> us = sort(1);
        for (int i = 0; i < users.size(); i++) {
            String u  = us.get(i);
            AtomicInteger win = new AtomicInteger();
            rs.stream().forEach(r -> {
                if (r.winner.equals(u)) win.getAndIncrement();
            });
            table.getItems().add(new TotalWin(win.get(),i + 1, u));
        }


    }

}
