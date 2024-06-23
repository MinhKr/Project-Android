package com.example.map_chat_app.Model;

public class Post {
    private int AvtPost;
    private String UsernamePost;
    private String TimePost;
    private int LikeIcon;
    private String LikeNum;
    private int CmtIcon;
    private String CmtNum;


    public Post(int AvtPost, String UsernamePost, String TimePost, int LikeIcon, String LikeNum, int CmtIcon, String CmtNum) {
        this.AvtPost = AvtPost;
        this.UsernamePost = UsernamePost;
        this.TimePost = TimePost;
        this.LikeIcon = LikeIcon;
        this.LikeNum = LikeNum;
        this.CmtIcon = CmtIcon;
        this.CmtNum = CmtNum;
    }

    public int getAvtPost() {
        return AvtPost;
    }

    public void setAvtPost(){
        this.AvtPost = AvtPost;
    }

    public String getUsernamePost() {
        return UsernamePost;
    }

    public void setUsernamePost(){
        this.UsernamePost = UsernamePost;
    }

    public String getTimePost() {
        return TimePost;
    }

    public void setTimePost(){
        this.TimePost = TimePost;
    }

    public int getLikeIcon() {
        return LikeIcon;
    }

    public void setLikeIcon(){
        this.LikeIcon = LikeIcon;
    }

    public String getLikeNum() {
        return LikeNum;
    }

    public void setLikeNum(){
        this.LikeNum = LikeNum;
    }

    public int getCmtIcon() {
        return CmtIcon;
    }

    public void setCmtIcon(){
        this.CmtIcon = CmtIcon;
    }

    public String getCmtNum() {
        return CmtNum;
    }

    public void setCmtNum(){
        this.CmtNum = CmtNum;
    }
}
