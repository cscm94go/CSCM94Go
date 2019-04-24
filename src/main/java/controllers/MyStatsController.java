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
import models.Users;
import org.json.JSONObject;
import java.io.*;
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
    private TableView<?> myGames;


    @FXML
    private Label winPercentage;

    @FXML
    private PieChart pieChart;


    @FXML
    void handleDashboardButtonAction(ActionEvent event) throws IOException {
        HelperMethods.LoadScene("/fxml/HomeDashboard.fxml");
    }

    public void setUserimage() {
        profile_image.setImage(new Image(Users.currentUser.image));
    }


    public void setPieChart() {
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                new PieChart.Data("Wins", 1),
                new PieChart.Data("Losses", 1),
                new PieChart.Data("Played", 2));


        pieChart.setData(pieChartData);
        pieChart.setStartAngle(0);
    }


    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        setUserimage();
    }

}