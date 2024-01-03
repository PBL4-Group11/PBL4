package com.example.demo1.controller;

import com.example.demo1.HelloApplication;
import com.example.demo1.model.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.net.InetAddress;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class AfterLoginController implements Initializable {
    private static AfterLoginController instance;
    @FXML
    public Button NTPButton;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TextArea textArea;
    @FXML
    private BorderPane loggedin;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Button logout;

    public AfterLoginController() {
    }

    public static AfterLoginController getInstance() {
        if (instance == null) {
            instance = new AfterLoginController();
        }
        return instance;
    }

    public void getTimeNTP(ActionEvent event) {
        NTPButton.setStyle("-fx-background-color: grey");
        NTPButton.setDisable(true);
        NTPUDPClient client = new NTPUDPClient();
        try {
            long localTime = System.currentTimeMillis();
            System.out.println(localTime);
            Date realTime = new Date(localTime); // Chuyển đổi thành đối tượng Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            System.out.println(dateFormat.format(realTime)); // In ra thời gian thực
            textArea.appendText("Current time: " + dateFormat.format(realTime) + "\n");
            NTPButton.setStyle("-fx-background-color: #5454ee");
            NTPButton.setDisable(false);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }

    @FXML
    public void welcomeName(String username) {
        welcomeLabel.setText("Welcome, " + username);
    }

    public void userLogOut(ActionEvent event) {
        HelloApplication m = new HelloApplication();
        Session session = Session.getInstance();
        session.clearSession();
        System.out.println(session.getCurrentUser());
        m.changeSceneLogin("login.fxml");

    }

    @FXML
    public void synchronizeTime(ActionEvent event) {
        NTPTimeSync ntpTimeSync = new NTPTimeSync();
        textArea.appendText("Time synchronized at " + ntpTimeSync.timeSync() + "\n");
    }

    @FXML
    public void timeDifference(ActionEvent event) {
        NTPTimeSync ntpTimeSync = new NTPTimeSync();
        textArea.appendText(ntpTimeSync.timeDifference());
    }

    public void clearLog(ActionEvent event) {
        textArea.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
