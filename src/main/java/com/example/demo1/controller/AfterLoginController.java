package com.example.demo1.controller;

import com.example.demo1.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AfterLoginController implements Initializable {
    private static AfterLoginController instance;
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
    @FXML
    public void welcomeName(String username) {
        welcomeLabel.setText("Welcome, " + username);
    }

    public void userLogOut(ActionEvent event) {
        HelloApplication m = new HelloApplication();
        m.changeSceneLogin("login.fxml");

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        HelloApplication helloApplication = new HelloApplication();
//        String username = helloApplication.getUserName();
//        System.out.println(username);
//        welcomeName(username);
    }
}
