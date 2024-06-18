package com.example.map_chat_app;

public class UserSQLite {
    private String phoneNumber;
    private String password;

    public UserSQLite(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }
}
