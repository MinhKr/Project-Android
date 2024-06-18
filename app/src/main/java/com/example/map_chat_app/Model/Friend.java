package com.example.map_chat_app.Model;

public class Friend {
    private String name;
    private int avatarResId; // Resource ID for the avatar image

    public Friend(String name, int avatarResId) {
        this.name = name;
        this.avatarResId = avatarResId;
    }

    public String getName() {
        return name;
    }

    public int getAvatarResId() {
        return avatarResId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatarResId(int avatarResId) {
        this.avatarResId = avatarResId;
    }
}
