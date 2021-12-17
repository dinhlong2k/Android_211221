package com.example.android_71221.Model;

import android.database.Cursor;

import com.example.android_71221.Admin.Model.Bill;
import com.example.android_71221.Admin.Model.Buy;
import com.example.android_71221.DB_Helper.DB_Helper;

import java.util.ArrayList;

public class BuyModel {
    private DB_Helper db_helper;

    public BuyModel(DB_Helper db_helper) {
        this.db_helper = db_helper;
    }

    public void CreateTableBuy(){
        db_helper.queryData("CREATE TABLE IF NOT EXISTS Buy(Id INTEGER PRIMARY KEY AUTOINCREMENT, IdBill REFERENCES Bill(Id), IdProduct REFERENCES Product(Id) ,Quatity INTEGER)");
    }

    public void InsertDataBuy(int idBill, Product product){
        db_helper.queryData("INSERT INTO Buy VALUES(NULL,'"+idBill+"','"+product.getId()+"', '"+product.getQuatity()+"')");
    }
    public void DeleteBuyTable(){
        db_helper.queryData("DROP TABLE Buy");
    }

    public ArrayList<Buy> GetBuyData(){
        ArrayList<Buy> buyArrayList = new ArrayList<Buy>();

        Cursor cursor = db_helper.getData("SELECT * FROM Buy");
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            int idBill = cursor.getInt(1);
            int idProduct = cursor.getInt(2);
            int qtt = cursor.getInt(3);
            Buy buy = new Buy(id, idProduct, qtt, idBill);
            buyArrayList.add(buy);
        }
        return buyArrayList;
    }

    public ArrayList<Buy> GetBuyById(int billID){
        ArrayList<Buy> buyArrayList = new ArrayList<Buy>();

        Cursor cursor = db_helper.getData("SELECT * FROM Buy WHERE IdBill = '"+billID+"'");
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            int idBill = cursor.getInt(1);
            int idProduct = cursor.getInt(2);
            int qtt = cursor.getInt(3);
            Buy buy = new Buy(id, idProduct, qtt, idBill);
            buyArrayList.add(buy);
        }
        return buyArrayList;
    }
}
