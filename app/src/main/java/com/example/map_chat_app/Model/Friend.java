package com.example.map_chat_app.Model;

public class Friend {
    private String name;
    private String id;
    private int avatarResId; // Resource ID for the avatar image

    public Friend(String name, int avatarResId) {
        this.name = name;
        this.id = id;
        this.avatarResId = avatarResId;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(){
        this.id=id;
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
