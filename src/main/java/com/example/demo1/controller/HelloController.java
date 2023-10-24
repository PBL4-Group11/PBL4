package com.example.demo1.controller;

import com.example.demo1.database.DBUtils;
import com.example.demo1.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginBtn;
    @FXML
    private Label wrongLogin;
    @FXML
    private Button registerBtn;

    DBUtils dbUtils = new DBUtils();
    private void checkLogin() {
        HelloApplication m = new HelloApplication();
        if (username.getText().isEmpty() || password.getText().isEmpty()) {
            wrongLogin.setText("Please enter your data.");
        } else {
            boolean flag = dbUtils.checkLogin(username.getText().toString(), password.getText().toString());
             if (flag) {
                wrongLogin.setText("Success!");
                m.changeSceneWelcome("afterLogin.fxml", username.getText());
            } else {
                wrongLogin.setText("Wrong username or password!");
            }
        }

    }

    private void registerForm() {
        HelloApplication m = new HelloApplication();
        m.changeSceneRegister("register.fxml");
    }

    public void userLogin(ActionEvent event) {
        checkLogin();
    }

    public void registerForm(ActionEvent event) {
        registerForm();
    }
}