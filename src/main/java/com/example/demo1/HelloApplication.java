package com.example.demo1;

import com.example.demo1.controller.AfterLoginController;
import com.example.demo1.model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    private static String username;
    private static Stage stg;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        try {
            stg = stage;
            stage.setResizable(false);
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            stage.setTitle("Login");
            stage.setScene(new Scene(root, 600, 340));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void changeSceneWelcome(String fxml, String username) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(fxml));
            Parent pane = (Parent) loader.load();
            AfterLoginController afterLoginController = loader.getController();
            afterLoginController.welcomeName(username);
            stg.setResizable(false);
            stg.setTitle("Welcome!");
            stg.setScene(new Scene(pane, 800, 600));
            stg.show();
            User user = new User(username);
            System.out.println(user.getUsername());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeSceneLogin(String fxml) {
        try {
            Parent pane = FXMLLoader.load(getClass().getResource(fxml));
            stg.setTitle("Login");
            stg.setResizable(false);
            stg.setScene(new Scene(pane, 600, 340));
            stg.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void changeSceneRegister(String fxml) {
        try {
            Parent pane = FXMLLoader.load(getClass().getResource(fxml));
            stg.setResizable(false);
            stg.setTitle("Register");
            stg.setScene(new Scene(pane, 600, 370));
            stg.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}