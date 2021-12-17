package com.example.android_71221.Model;

public class Card {
    private int id;
    private String name;
    private String number;
    private String cvv;
    private String exp;
    private int cardType;

    public Card() {
    }

    public Card(int id, String name, String number, String cvv, String exp, int cardType) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.cvv = cvv;
        this.exp = exp;
        this.cardType = cardType;
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

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", cvv='" + cvv + '\'' +
                ", exp='" + exp + '\'' +
                ", cardType=" + cardType +
                '}';
    }
}
