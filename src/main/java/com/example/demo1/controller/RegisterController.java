package com.example.demo1.controller;

import com.example.demo1.database.DBUtils;
import com.example.demo1.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegisterController {
    @FXML
    private Button registerBtn;
    @FXML
    private Button returnLogin;
    @FXML
    private TextField usernameReg;
    @FXML
    private TextField passwordReg;
    @FXML
    private TextField confPasswordReg;
    @FXML
    private Label errorReg;

    public void returnLogin(ActionEvent event) {
        HelloApplication m = new HelloApplication();
        m.changeSceneLogin("login.fxml");
    }

    public void register(ActionEvent event) {
        DBUtils dbUtils = new DBUtils();
        if (usernameReg.getText().isEmpty() || passwordReg.getText().isEmpty() || confPasswordReg.getText().isEmpty()) {
            errorReg.setText("Please enter your data.");
        } else if (passwordReg.getText().equals(confPasswordReg.getText())) {
            errorReg.setText("Success!");
            dbUtils.register(usernameReg.getText(), passwordReg.getText());
            HelloApplication m = new HelloApplication();
            m.changeSceneLogin("login.fxml");
        } else {
            errorReg.setText("Password should match with confirm password!");
        }

    }
}
