package com.example.demo1.database;

import com.example.demo1.model.User;

import java.sql.*;
import java.util.ArrayList;

public class DBUtils {
    public Connection connect2DB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver OK");
            String URL = "jdbc:mysql://localhost/pbl4";
            String USER = "root";
            String PASS = "";
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkLogin(String username, String password){
        ArrayList<User> users = new ArrayList<>();
        Connection connection = connect2DB();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM `user` where username = ? AND password = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String userName = resultSet.getString("username");
                String passWord = resultSet.getString("password");
                User user = new User(userName, passWord);
                users.add(user);
                System.out.println(user.getUsername() + user.getPassword());
            }
            if (users.isEmpty()) {
                connection.close();
                return false;
            } else {
                connection.close();
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void register(String username, String password){
        Connection connection = connect2DB();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO `user` (username, password) VALUES (? , ?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.execute();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
