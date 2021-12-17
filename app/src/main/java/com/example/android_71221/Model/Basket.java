package com.example.android_71221.Model;

import java.util.ArrayList;

public class Basket {
//    public static int id;
    public static int idUser;
    public static ArrayList<Product> basket;
    public static String time;

    public static int getIdUser() {
        return idUser;
    }

    public static void setIdUser(int idUser) {
        Basket.idUser = idUser;
    }

    public static ArrayList<Product> getBasket() {
        return basket;
    }

    public static void setBasket(ArrayList<Product> basket) {
        Basket.basket = basket;
    }

    public static String getTime() {
        return time;
    }

    public static void setTime(String time) {
        Basket.time = time;
    }

    public static Double TotalPrice(){
        double sum = 0;
        for (int i = 0 ; i < Basket.basket.size();i++){
            Product product = Basket.getBasket().get(i);
            sum += product.getPrice() * product.getQuatity();
        }
        return sum;
    }
}
