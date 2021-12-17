package com.example.android_71221.Model;

public class CardType {
    private int id;
    private String name;

    public CardType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CardType(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
