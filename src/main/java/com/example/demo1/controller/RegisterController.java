package com.example.demo1.controller;

import com.example.demo1.database.DBUtils;
import com.example.demo1.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
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

    @FXML
    private void registerButton(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)
                || event.getCharacter().getBytes()[0] == '\n'
                || event.getCharacter().getBytes()[0] == '\r') {
            checkRegister();
        }
    }

    public void register(ActionEvent event) {
        checkRegister();
    }

    private void checkRegister() {
        DBUtils dbUtils = new DBUtils();
        if (usernameReg.getText().isEmpty() || passwordReg.getText().isEmpty() || confPasswordReg.getText().isEmpty()) {
            errorReg.setText("Please enter your data.");
        } else if (dbUtils.isExist(usernameReg.getText())) {
            errorReg.setText("Username existed.");
        } else if (passwordReg.getText().equals(confPasswordReg.getText())) {
            errorReg.setText("Success!");
            dbUtils.register(usernameReg.getText(), passwordReg.getText());
            HelloApplication m = new HelloApplication();
            m.changeSceneLogin("login.fxml");
        } else {
            errorReg.setText("Password should match with confirm password!");
        }
    }

    private void handleArrowKeys(KeyEvent event) {
        KeyCode keyCode = event.getCode();

        // Check which arrow key is pressed
        if (keyCode == KeyCode.UP) {
            focusPreviousTextField();
        } else if (keyCode == KeyCode.DOWN) {
            focusNextTextField();
        }
    }

    private void focusPreviousTextField() {
        if (passwordReg.isFocused()) {
            usernameReg.requestFocus();
        } else if (confPasswordReg.isFocused()) {
            passwordReg.requestFocus();
        }
    }

    private void focusNextTextField() {
        if (usernameReg.isFocused()) {
            passwordReg.requestFocus();
        } else if (passwordReg.isFocused()) {
            confPasswordReg.requestFocus();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameReg.setOnKeyPressed(this::handleArrowKeys);
        passwordReg.setOnKeyPressed(this::handleArrowKeys);
        confPasswordReg.setOnKeyPressed(this::handleArrowKeys);
    }
}
