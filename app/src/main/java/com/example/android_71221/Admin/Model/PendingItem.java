package com.example.android_71221.Admin.Model;

import java.util.ArrayList;

public class PendingItem {
    private int idBill;
    private String customerName;
    private ArrayList<Product_Qtt> productQttArrayList;
    private String time;
    private Double price;

    public PendingItem(int idBill, String customerName, ArrayList<Product_Qtt> productQttArrayList, String time, Double price) {
        this.idBill = idBill;
        this.customerName = customerName;
        this.productQttArrayList = productQttArrayList;
        this.time = time;
        this.price = price;
    }

    public int getIdBill() {
        return idBill;
    }

    public void setIdBill(int idBill) {
        this.idBill = idBill;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public ArrayList<Product_Qtt> getProductQttArrayList() {
        return productQttArrayList;
    }

    public void setProductQttArrayList(ArrayList<Product_Qtt> productQttArrayList) {
        this.productQttArrayList = productQttArrayList;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
