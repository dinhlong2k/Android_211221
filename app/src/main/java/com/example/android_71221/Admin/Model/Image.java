package com.example.android_71221.Admin.Model;

import com.google.gson.annotations.SerializedName;

public class Image {
    private int accountId;
    private String username;
    private String  password;
    @SerializedName("avt")
    private String image;


    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
