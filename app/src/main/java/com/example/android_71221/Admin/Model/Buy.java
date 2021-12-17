package com.example.android_71221.Admin.Model;

public class Buy {
    private int id;
    private int idProduct;
    private int qtt;
    private int idBill;

    public Buy(int id, int idProduct, int qtt, int idBill) {
        this.id = id;
        this.idProduct = idProduct;
        this.qtt = qtt;
        this.idBill = idBill;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getQtt() {
        return qtt;
    }

    public void setQtt(int qtt) {
        this.qtt = qtt;
    }

    public int getIdBill() {
        return idBill;
    }

    public void setIdBill(int idBill) {
        this.idBill = idBill;
    }
}
