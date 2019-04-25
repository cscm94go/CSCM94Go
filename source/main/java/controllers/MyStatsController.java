package controllers;
import application.Main;
import com.jfoenix.controls.JFXButton;
import com.sun.org.apache.xml.internal.utils.res.StringArrayWrapper;
import helpers.HelperMethods;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.Admin;
import models.Record;
import models.Users;
import org.json.JSONObject;
import java.io.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.nio.file.Files;
import java.util.Date;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * This class can ...
 * @author Hector
 * @version 1.0
 */
public class MyStatsController implements Initializable {
    /**
     * This
     */
    private static MyStatsController instance;

    /**
     * This
     */
    public MyStatsController() {
        instance = this;
    }


    public static MyStatsController getInstance() {
        return instance;
    }


    @FXML
    public ImageView profile_image;

    @FXML
    private TextField user_name;

    @FXML
    private TextField last_name;

    @FXML
    private TextField first_name;

    @FXML
    JFXButton backtoDashboard;

    @FXML
    private TableView<Record> myGames;


    @FXML
    private Label winPercentage;

    @FXML
    private PieChart pieChart;

    @FXML
    private Label myUsername;

    public void setUsername(String user) {
        this.myUsername.setText(user);
    }

    @FXML
    void handleDashboardButtonAction(ActionEvent event) throws IOException {
        HelperMethods.LoadScene("/fxml/HomeDashboard.fxml");
    }

    public void setUserimage() {
        profile_image.setImage(new Image(Users.currentUser.image));
    }


    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUsername(AccountController.getInstance().username());

        double win_percentage = Users.getCurrentUserWinPercentage();
        String yourWinP = ("Your win perentage = " + win_percentage + " %");
        this.winPercentage.setText(yourWinP);
        setUserimage();

        int [] pieData = Users.getCurrentUserWinsAndLosesCount();

        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                new PieChart.Data("Losses",pieData[1]),
                new PieChart.Data("Wins",pieData[0]));

        pieChart.setData(pieChartData);
        pieChart.setStartAngle(0);

        TableColumn<Record, String> c1 = new TableColumn<>("Opponent");
        c1.setPrefWidth(100);
        c1.setCellValueFactory(cell -> {
            Record r = cell.getValue();
            String opponent = r.player1.equals(Users.currentUser.username) ? r.player2 : r.player1;
            return new ReadOnlyStringWrapper(opponent);
        });
        TableColumn<Record, String> c2 = new TableColumn<>("Result");
        c2.setPrefWidth(100);
        c2.setCellValueFactory(cell -> {
            Record r = cell.getValue();
            String res = r.winner.equals(Users.currentUser.username) ? "Won" : "Lost";
            return new ReadOnlyStringWrapper(res);
        });
        TableColumn<Record, String> c3 = new TableColumn<>("Time");
        c3.setPrefWidth(200);
        c3.setCellValueFactory(cell -> {
            Record r = cell.getValue();
            String time = new Date(r.timeStamp).toLocaleString();
            return new ReadOnlyStringWrapper(time);
        });
        myGames.getColumns().add(c1);
        myGames.getColumns().add(c2);
        myGames.getColumns().add(c3);

        List<Record> rs = Users.getCurrentUserGameRecord();
        for (int i = 0; i < rs.size(); i++) {
            myGames.getItems().add(rs.get(i));
        }

    }
}