package com.example.demo1.model;

public class Session {
    private static Session instance;
    private User currentUser;

    private Session() {
        // Private constructor to prevent instantiation from outside
    }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void clearSession() {
        currentUser = null;
    }
}
