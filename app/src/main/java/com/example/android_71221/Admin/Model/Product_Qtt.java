package com.example.android_71221.Admin.Model;

public class Product_Qtt {
    private String productName;
    private int qtt;

    public Product_Qtt(String productName, int qtt) {
        this.productName = productName;
        this.qtt = qtt;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQtt() {
        return qtt;
    }

    public void setQtt(int qtt) {
        this.qtt = qtt;
    }
}

