package com.example.android_71221.Admin.Model;

public class Bill {
    private int id;
    private int idUser;
    private String time;
    private Double price;

    public Bill(int id, int idUser, String time, Double price) {
        this.id = id;
        this.idUser = idUser;
        this.time = time;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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
