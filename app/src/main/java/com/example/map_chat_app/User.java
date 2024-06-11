package com.example.map_chat_app;

public class User {
    public String userId;
    public String phoneNumber;

    // Default constructor required for calls to DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String userId, String phoneNumber) {
        this.userId = userId;
        this.phoneNumber = phoneNumber;
    }
}
