package com.example.android_71221.Model;

import android.database.Cursor;

import com.example.android_71221.Admin.Model.Bill;
import com.example.android_71221.Admin.Model.Buy;
import com.example.android_71221.Admin.Model.PendingItem;
import com.example.android_71221.Admin.Model.Product_Qtt;
import com.example.android_71221.DB_Helper.DB_Helper;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class BillModel {
    private DB_Helper db_helper;

    public BillModel(DB_Helper db_helper) {
        this.db_helper = db_helper;
    }

    public void CreateTableBill(){
        db_helper.queryData("CREATE TABLE IF NOT EXISTS Bill(Id INTEGER PRIMARY KEY AUTOINCREMENT, IdUser REFERENCES User(Id),Price REAL , Time VARCHAR(255))");
    }

    public void InsertDataBill(String time, int userId,Double price){
        db_helper.queryData("INSERT INTO Bill VALUES(NULL,'"+userId+"','"+price+"','"+time+"')");

    }
    public void DeleteBillTable(){
        db_helper.queryData("DROP TABLE Bill");
    }

    public ArrayList<Bill> GetBillData(){
        ArrayList<Bill> billArrayList = new ArrayList<Bill>();

        Cursor cursor = db_helper.getData("SELECT * FROM Bill");
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            int idUser = cursor.getInt(1);
            double price = cursor.getDouble(2);
            String time = cursor.getString(3);
            Bill bill = new Bill(id, idUser,time, price);
            billArrayList.add(bill);
        }
        return billArrayList;
    }

    public ArrayList<Bill> GetBillDataByID(int userID){
        ArrayList<Bill> billArrayList = new ArrayList<Bill>();

        Cursor cursor = db_helper.getData("SELECT * FROM Bill WHERE IdUser = '"+userID+"'");
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            int idUser = cursor.getInt(1);
            double price = cursor.getDouble(2);
            String time = cursor.getString(3);
            Bill bill = new Bill(id, idUser,time, price);
            billArrayList.add(bill);
        }
        return billArrayList;
    }

    public int FindLastestBill(){
        int idBill = 0;
        Cursor cursor = db_helper.getData("SELECT * FROM Bill ORDER BY Id DESC LIMIT 1");
        while (cursor.moveToNext()){
            idBill = cursor.getInt(0);
        }
        return idBill;
    }

    public ArrayList<PendingItem> PopulateDataFromBillID(ArrayList<Bill> bills){
        ArrayList<PendingItem> pendingItemArrayList = new ArrayList<PendingItem>();
        ArrayList<Bill> billArrayList = bills;
        ArrayList<Buy> buyArrayList = new ArrayList<Buy>();
        UserModel userModel = new UserModel(db_helper);
        ProductModel productModel = new ProductModel(db_helper);
        BuyModel buyModel = new BuyModel(db_helper);
        for (int i = 0 ; i < billArrayList.size(); i++){
            // detect bill & user
            Bill bill = billArrayList.get(i);
            User user = userModel.getUser(bill.getIdUser());

            String time = bill.getTime();
            int idBill = bill.getId();
            String customerName = user.getFullName();
            Double price = bill.getPrice();

            // detect product
            buyArrayList = buyModel.GetBuyById(idBill);
            ArrayList<Product_Qtt> productQttArrayList = new ArrayList<Product_Qtt>();
            for (int j = 0; j < buyArrayList.size();j++){
                Buy buy = buyArrayList.get(j);
                Product product = productModel.GetProductById(buy.getIdProduct());
                String name = product.getName();
                int qtt = buy.getQtt();
                Product_Qtt product_qtt = new Product_Qtt(name, qtt);
                productQttArrayList.add(product_qtt);
            }
            PendingItem pendingItem = new PendingItem(idBill, customerName, productQttArrayList, time,price);
            pendingItemArrayList.add(pendingItem);
        }
        return pendingItemArrayList;

    }

}
