package com.example.android_71221.Model;

public class Stock {

    private int qtt;
    private String label;

    public Stock(int qtt, String label) {
        this.qtt = qtt;
        this.label = label;
    }

    public int getQtt() {
        return qtt;
    }

    public void setQtt(int qtt) {
        this.qtt = qtt;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
