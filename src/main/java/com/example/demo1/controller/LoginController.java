package com.example.demo1.controller;

import com.example.demo1.HelloApplication;
import com.example.demo1.database.DBUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    DBUtils dbUtils = new DBUtils();
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
    public boolean isScreenChanged = false;

    @FXML
    private void loginButton(KeyEvent event) {
        loginBtn.setOnKeyPressed(event1 -> {
            if (event1.getCode() == KeyCode.ENTER) {
                // Handle Enter key press
                loginBtn.fire(); // Simulate button click
            }
        });
        if (event.getCode().equals(KeyCode.ENTER)
                || event.getCharacter().getBytes()[0] == '\n'
                || event.getCharacter().getBytes()[0] == '\r') {
            checkLogin();
        }
    }

    private void checkLogin() {
        HelloApplication m = new HelloApplication();
        if (username.getText().isEmpty() || password.getText().isEmpty()) {
            wrongLogin.setText("Please enter your data.");
        } else {
            boolean flag = dbUtils.checkLogin(username.getText(), password.getText());
            if (flag) {
                wrongLogin.setText("Success!");
                m.changeSceneWelcome("afterLogin.fxml", username.getText(), password.getText());
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

    private void handleArrowKeys(KeyEvent event) {
        KeyCode keyCode = event.getCode();

        // Check which arrow key is pressed
        if (keyCode == KeyCode.UP) {
            focusPreviousTextField();
        } else if (keyCode == KeyCode.DOWN) {
            focusNextTextField();
        } else if (keyCode == KeyCode.ENTER) {
            if (!isScreenChanged) {
                isScreenChanged = true;
                checkLogin();
                System.out.println(isScreenChanged);
                System.out.println("Switching to another screen...");
            } else {
                System.out.println(isScreenChanged);
                System.out.println("Performing action on the second screen...");
            }
        }
    }

    private void focusPreviousTextField() {
        if (password.isFocused()) {
            username.requestFocus();
        }
    }

    private void focusNextTextField() {
        if (username.isFocused()) {
            password.requestFocus();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isScreenChanged = false;
        username.setOnKeyPressed(this::handleArrowKeys);
        password.setOnKeyPressed(this::handleArrowKeys);
    }
}