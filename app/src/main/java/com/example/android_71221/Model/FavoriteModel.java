package com.example.android_71221.Model;

import android.database.Cursor;

import com.example.android_71221.DB_Helper.DB_Helper;

import java.util.ArrayList;

public class FavoriteModel {
    private DB_Helper db_helper;

    public FavoriteModel(DB_Helper db_helper) {
        this.db_helper = db_helper;
    }

    public void CreateTableFavorite(){
        db_helper.queryData("CREATE TABLE IF NOT EXISTS Favorite(Id INTEGER PRIMARY KEY AUTOINCREMENT, IdProduct INTEGER REFERENCES Product(Id), IdUser INTEGER REFERENCES User(Id))");
    }

    public void InsertFavoriteData(int idUser, int idProduct){
        db_helper.queryData("INSERT INTO Favorite VALUES(NULL, '"+idProduct+"', '"+idUser+"')");
    }

    public void DeleteFavoriteData(int idUser, int idProduct){
        int id = FindID(idUser, idProduct);
        if (id != -1){
            db_helper.queryData("DELETE FROM Favorite WHERE Id = '"+id+"'");
        }
    }

    public int FindID(int idUser, int idProduct){
        Cursor cursor = db_helper.getData("SELECT * FROM Favorite WHERE IdProduct = '"+idProduct+"' AND IdUser = '"+idUser+"'");
        int id = -1;
        while (cursor.moveToNext()){
            id = cursor.getInt(0);
        }
        return id;
    }

    public Boolean IsFavorite(int idUser, int idProduct){
        int id = FindID(idUser,idProduct);
        if (id != -1){
            return true;
        }
        return false;
    }

    public ArrayList<Integer> SelectFavProduct(int idUser){
        ArrayList<Integer> productIDs = new ArrayList<Integer>();
        Cursor cursor = db_helper.getData("SELECT * FROM Favorite WHERE IdUser = '"+idUser+"'");
        while (cursor.moveToNext()){
            int idProduct = cursor.getInt(1);
            productIDs.add(idProduct);
        }
        return productIDs;
    }
}
