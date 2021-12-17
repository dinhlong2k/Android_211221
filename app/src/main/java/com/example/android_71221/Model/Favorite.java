package com.example.android_71221.Model;

public class Favorite {
    private int id;
    private int idUser;
    private int idProduct;

    public Favorite(int id, int idUser, int idProduct) {
        this.id = id;
        this.idUser = idUser;
        this.idProduct = idProduct;
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

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }
}
