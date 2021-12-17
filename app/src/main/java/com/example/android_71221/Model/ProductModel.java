package com.example.android_71221.Model;

import android.database.Cursor;
import android.util.Log;

import com.example.android_71221.DB_Helper.DB_Helper;

import java.util.ArrayList;

public class ProductModel {
    private DB_Helper db_helper;

    public ProductModel(DB_Helper db_helper) {
        this.db_helper = db_helper;
    }

    public void CreateTableProduct(){
        db_helper.queryData("CREATE TABLE IF NOT EXISTS Product(Id INTEGER PRIMARY KEY AUTOINCREMENT, Name VARCHAR(255), Version VARCHAR(255),Description TEXT, " +
                "PRICE REAL, Quatity INTEGER, CategoryId REFERENCES Category(Id))");
    }
    public void CreateTableImages(){
        db_helper.queryData("CREATE TABLE IF NOT EXISTS ProductImage(Id INTEGER PRIMARY KEY AUTOINCREMENT, ImageURL VARCHAR(255) ,IdProduct INTEGER REFERENCES Product(Id))");
    }

    public void InsertProduct(Product product){
        db_helper.queryData("INSERT INTO Product VALUES(NULL, '"+product.getName()+"', '"+product.getVersion()+"', '"+product.getDescription()+"', '"+product.getPrice()+"', '"+product.getQuatity()+"', '"+product.getCategoryId()+"')");
    }

    public void InsertImg(Product product){
        ArrayList<String> imgURLs = product.getImages();
        // get latest id
        Cursor cursor = db_helper.getData("SELECT * FROM Product ORDER BY Id DESC LIMIT 1");
        int idProduct = 0;
        while (cursor.moveToNext()){
            idProduct = cursor.getInt(0);
        }

        for (int i = 0; i < imgURLs.size();i++){
            Log.d("Insert Img", "InsertImg: "+String.valueOf(idProduct));
            db_helper.queryData("INSERT INTO ProductImage VALUES(NULL, '"+imgURLs.get(i)+"', '"+idProduct+"')");
        }
    }
    public void DeleteTable(String name){
        db_helper.queryData("DROP TABLE '"+name+"'");
    }


    public ArrayList<Product> filterProduct(int catId){
        ArrayList<Product> productArrayList = new ArrayList<Product>();
        // detect IdProduct to select

        Cursor queryProduct = db_helper.getData("SELECT * FROM Product WHERE CategoryId LIKE '"+catId+"'");
        while (queryProduct.moveToNext()){
            int id = queryProduct.getInt(0);
            String name = queryProduct.getString(1);
            String version = queryProduct.getString(2);
            String description = queryProduct.getString(3);
            Double price = queryProduct.getDouble(4);
            int qtt = queryProduct.getInt(5);
            int categoryId = queryProduct.getInt(6);
            ArrayList<String> imgUrls = new ArrayList<>();
            Cursor queryImg = db_helper.getData("SELECT * FROM ProductImage WHERE IdProduct LIKE '"+id+"'");
            while (queryImg.moveToNext()){
                String url = queryImg.getString(1);
                imgUrls.add(url);
            }
            Log.d("img", String.valueOf(imgUrls.size()));
            Product product = new Product(id, name, version, price, qtt, description, imgUrls, categoryId);
            productArrayList.add(product);
        }

        return productArrayList;

    }

    public ArrayList<Product> DisplayProduct(){
        ArrayList<Product> productArrayList = new ArrayList<Product>();
        // detect IdProduct to select

        Cursor queryProduct = db_helper.getData("SELECT * FROM Product");
        while (queryProduct.moveToNext()){
            int id = queryProduct.getInt(0);
            String name = queryProduct.getString(1);
            String version = queryProduct.getString(2);
            String description = queryProduct.getString(3);
            Double price = queryProduct.getDouble(4);
            int qtt = queryProduct.getInt(5);
            int categoryId = queryProduct.getInt(6);
            ArrayList<String> imgUrls = new ArrayList<>();
            Cursor queryImg = db_helper.getData("SELECT * FROM ProductImage WHERE IdProduct LIKE '"+id+"'");
            while (queryImg.moveToNext()){
                String url = queryImg.getString(1);
                imgUrls.add(url);
            }
            Log.d("img", String.valueOf(imgUrls.size()));
            Product product = new Product(id, name, version, price, qtt, description, imgUrls, categoryId);
            productArrayList.add(product);
        }

        return productArrayList;
    }

    public Product GetProductById(int productID){
        ArrayList<Product> productArrayList = new ArrayList<Product>();
        // detect IdProduct to select

        Cursor queryProduct = db_helper.getData("SELECT * FROM Product WHERE Id = '"+productID+"'");
        while (queryProduct.moveToNext()){
            int id = queryProduct.getInt(0);
            String name = queryProduct.getString(1);
            String version = queryProduct.getString(2);
            String description = queryProduct.getString(3);
            Double price = queryProduct.getDouble(4);
            int qtt = queryProduct.getInt(5);
            int categoryId = queryProduct.getInt(6);
            ArrayList<String> imgUrls = new ArrayList<>();
            Cursor queryImg = db_helper.getData("SELECT * FROM ProductImage WHERE IdProduct LIKE '"+id+"'");
            while (queryImg.moveToNext()){
                String url = queryImg.getString(1);
                imgUrls.add(url);
            }
            Log.d("img", String.valueOf(imgUrls.size()));
            Product product = new Product(id, name, version, price, qtt, description, imgUrls, categoryId);
            productArrayList.add(product);
        }

        return productArrayList.get(0);
    }


    public void DeleteProduct(Product product){
        db_helper.queryData("DELETE FROM Product WHERE Id = '"+product.getId()+"'");
    }

    public void UpdateProductQTT(Product product){
        Cursor queryProduct = db_helper.getData("SELECT * FROM Product WHERE Id = '"+product.getId()+"'");
        int qtt = 0;
        while (queryProduct.moveToNext()){
            qtt = queryProduct.getInt(5);
        }
        int remainQTT = qtt - product.getQuatity();
        db_helper.queryData("UPDATE Product SET Quatity = '"+remainQTT+"' WHERE Id = '"+product.getId()+"'");
    }

    public int ProductIn(){
        Cursor queryProduct = db_helper.getData("SELECT * FROM Product");

        int productIn = 0;
        while (queryProduct.moveToNext()){
            int qtt = queryProduct.getInt(5);
            productIn += qtt;
        }
        return productIn;
    }

    public int ProductOut(){
        Cursor queryProduct = db_helper.getData("SELECT * FROM Buy");
        int productOut = 0;
        while (queryProduct.moveToNext()){
            int qtt = queryProduct.getInt(3);
            productOut += qtt;
        }
        return productOut;
    }
}
