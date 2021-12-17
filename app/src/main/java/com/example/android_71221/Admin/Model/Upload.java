package com.example.android_71221.Admin.Model;

public class Upload {
    private String mName;
    private String mImageURL;

    public Upload(){

    }

    public Upload(String mName, String mImageURL) {
        if (mName.trim().equals("")){
            mName = "N/A";
        }
        this.mName = mName;
        this.mImageURL = mImageURL;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImageURL() {
        return mImageURL;
    }

    public void setmImageURL(String mImageURL) {
        this.mImageURL = mImageURL;
    }
}
