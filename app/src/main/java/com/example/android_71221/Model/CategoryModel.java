package com.example.android_71221.Model;

import android.database.Cursor;

import com.example.android_71221.DB_Helper.DB_Helper;

import java.util.ArrayList;

public class CategoryModel {
    private DB_Helper db_helper;

    public CategoryModel(DB_Helper db_helper) {
        this.db_helper = db_helper;
    }

    public DB_Helper getDb_helper() {
        return db_helper;
    }

    public void setDb_helper(DB_Helper db_helper) {
        this.db_helper = db_helper;
    }

    public void CreateTableCategory(){
        db_helper.queryData("CREATE TABLE IF NOT EXISTS Category(Id INTEGER PRIMARY KEY AUTOINCREMENT, Name VARCHAR(255))");
    }

    public void InsertCategory(Category category){
        db_helper.queryData("INSERT INTO Category VALUES(NULL, '"+category.getName()+"')");
    }

    public void UpdateCategory(Category category){
        db_helper.queryData("UPDATE Category SET Name = '"+category.getName()+"' WHERE Id = '"+category.getId()+"'");
    }

    public ArrayList<Category> DisplayCategory(){
        ArrayList<Category> categoryArrayList = new ArrayList<Category>();
        Cursor cursor = db_helper.getData("SELECT * FROM Category");
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            Category category = new Category(id, name);
            categoryArrayList.add(category);
        }
        return categoryArrayList;
    }

}
