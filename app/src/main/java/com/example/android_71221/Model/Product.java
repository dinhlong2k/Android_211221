package com.example.android_71221.Model;

import java.util.ArrayList;

public class Product {
    private int id;
    private String name;
    private String version;
    private Double price;
    private int quatity;
    private String description;
    private ArrayList<String> images;
    private int categoryId;

    public Product(int id, String name, String version, Double price, int quatity, String description, ArrayList<String> images, int categoryId) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.price = price;
        this.quatity = quatity;
        this.description = description;
        this.images = images;
        this.categoryId = categoryId;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuatity() {
        return quatity;
    }

    public void setQuatity(int quatity) {
        this.quatity = quatity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
