package com.example.android_71221.Model;

import android.database.Cursor;

import androidx.annotation.NonNull;

import com.example.android_71221.DB_Helper.DB_Helper;

import java.util.ArrayList;

public class UserModel {
    private DB_Helper db_helper;

    public UserModel(DB_Helper db_helper) {
        this.db_helper = db_helper;
    }

    public DB_Helper getDb_helper() {
        return db_helper;
    }

    public void setDb_helper(DB_Helper db_helper) {
        this.db_helper = db_helper;
    }

    //    QUERY USER DATA
    public void CreateTableUser(){
        db_helper.queryData("CREATE TABLE IF NOT EXISTS User(Id INTEGER PRIMARY KEY AUTOINCREMENT, Email VARCHAR(255), " +
                "Password VARCHAR(255), FirstName VARCHAR(255), LastName VARCHAR(255), PhoneNumber VARCHAR(255),Role INTEGER )");
    }

    public void UpdateDataUser(User user){
        db_helper.queryData("UPDATE User SET FirstName = '"+user.getFirstName()+"'," +
                "LastName= '"+user.getLastName()+"',PhoneNumber= '"+user.getPhoneNumber()+"' WHERE Id = '"+user.getId()+"'");
    }

    public void InsertDataUser(User user){
        db_helper.queryData("INSERT INTO User VALUES(NULL,'"+user.getEmail()+"','"+user.getPassword()+"'," +
                "'"+user.getFirstName()+"','"+user.getLastName()+"',NULL,'"+user.getRole()+"')");
    }

    public User login(String email,String password){
        ArrayList<User> userArrayList = new ArrayList<User>();
        Cursor userData = db_helper.getData("SELECT * FROM User WHERE email='"+email+"' AND password='"+password+"' ");

        if(userData.moveToFirst()){
            int id = userData.getInt(0);
            String email1 = userData.getString(1);
            String password1 = userData.getString(2);
            String firstName = userData.getString(3);
            String lastName = userData.getString(4);
            String phoneNumber = userData.getString(5);
            int role=userData.getInt(6);
            User user = new User(id, email1, password1, firstName, lastName, phoneNumber,role);
            userArrayList.add(user);
        }

        if(userArrayList.size()>0) return userArrayList.get(0);
        else return null;
    }

    public User getUser(int id){
        ArrayList<User> userArrayList = new ArrayList<User>();
        Cursor userData = db_helper.getData("SELECT * FROM User WHERE id= '"+id+"'");

        if(userData.moveToFirst()){
            int id1 = userData.getInt(0);
            String email1 = userData.getString(1);
            String password1 = userData.getString(2);
            String firstName = userData.getString(3);
            String lastName = userData.getString(4);
            String phoneNumber = userData.getString(5);
            User user = new User(id1, email1, password1, firstName, lastName, phoneNumber);
            userArrayList.add(user);
        }
        if(userArrayList.size()>0) return userArrayList.get(0);
        else return null;
    }

    public boolean checkEmailExits(String email){
        ArrayList<User> userArrayList = new ArrayList<User>();
        Cursor userData = db_helper.getData("SELECT * FROM User Where email= '"+email+"'");

        while (userData.moveToNext()){
            int id = userData.getInt(0);
            String email1 = userData.getString(1);
            String password = userData.getString(2);
            String firstName = userData.getString(3);
            String lastName = userData.getString(4);
            String phoneNumber = userData.getString(5);
            User user = new User(id, email1, password, firstName, lastName, phoneNumber);
            userArrayList.add(user);
        }

        if(userArrayList.size()  >0) return true;
        else return false;
    }

//    QUERY ADDRESS DATA FOLLOWING USER

    public void CreateTableAddress(){
        db_helper.queryData("CREATE TABLE IF NOT EXISTS Address(Id INTEGER PRIMARY KEY AUTOINCREMENT, Province VARCHAR(255), " +
                "District VARCHAR(255), Detail VARCHAR(255), IdUser REFERENCES User(Id) )");
    }

    public void InsertDataAddress(Address address, int userId){
        db_helper.queryData("INSERT INTO Address VALUES(NULL,NULL,NULL,NULL, '"+userId+"')");
    }

    public void UpdateDataAddress(@NonNull Address address){
        db_helper.queryData("UPDATE Address SET Province = '"+ address.getProvince()+"', District = '"+ address.getDistrict()+"'," +
                "Detail = '"+address.getDetail()+"' WHERE Id = '"+address.getId()+"'");
    }

    public ArrayList<User> DisplayUserData(){
        ArrayList<User> userArrayList = new ArrayList<User>();
        Cursor userData = db_helper.getData("SELECT * FROM User");

        while (userData.moveToNext()){
            int id = userData.getInt(0);
            String email = userData.getString(1);
            String password = userData.getString(2);
            String firstName = userData.getString(3);
            String lastName = userData.getString(4);
            String phoneNumber = userData.getString(5);
            User user = new User(id, email, password, firstName, lastName, phoneNumber);
            userArrayList.add(user);
        }
        return userArrayList;
    }

    public ArrayList<Address> DisplayAddressData(User user){
        ArrayList<Address> addressArrayList = new ArrayList<Address>();
        Cursor addressData = db_helper.getData("SELECT * FROM Address Where IdUser = '"+user.getId()+"'");
        while(addressData.moveToNext()){
            int id = addressData.getInt(0);
            String province = addressData.getString(1);
            String district = addressData.getString(2);
            String detail = addressData.getString(3);
            Address address = new Address(id, province, district, detail);
            addressArrayList.add(address);
        }
        return addressArrayList;
    }
    public Address getAddress(int id_user){
        ArrayList<Address> AddressArraylist = new ArrayList<Address>();
        Cursor addressData = db_helper.getData("SELECT * FROM Address WHERE IdUser= '"+id_user+"'");

        if(addressData.moveToFirst()){
            int id1 = addressData.getInt(0);
            String province = addressData.getString(1);
            String district = addressData.getString(2);
            String detail = addressData.getString(3);
            Address address = new Address(id1, province, district, detail);
            AddressArraylist.add(address);
        }
        if(AddressArraylist.size()>0) return AddressArraylist.get(0);
        else return null;
    }
    public User getUsersession(int id){
        ArrayList<User> userArrayList = new ArrayList<User>();
        Cursor userData = db_helper.getData("SELECT * FROM User WHERE id= '"+id+"'");

        if(userData.moveToFirst()){
            int id1 = userData.getInt(0);
            String email1 = userData.getString(1);
            String password1 = userData.getString(2);
            String firstName = userData.getString(3);
            String lastName = userData.getString(4);
            String phoneNumber = userData.getString(5);
            int role=userData.getInt(6);
            User user = new User(id1, email1, password1, firstName, lastName, phoneNumber,role);
            userArrayList.add(user);
        }
        if(userArrayList.size()>0) return userArrayList.get(0);
        else return null;
    }

    public ArrayList<User> getUserWithRoleUser(){
        ArrayList<User> userArrayList = new ArrayList<>();
        Cursor userData = db_helper.getData("SELECT * FROM User WHERE Role=1");

        while (userData.moveToNext()){
            int id = userData.getInt(0);
            String email = userData.getString(1);
            String password = userData.getString(2);
            String firstName = userData.getString(3);
            String lastName = userData.getString(4);
            String phoneNumber = userData.getString(5);
            User user = new User(id, email, password, firstName, lastName, phoneNumber);
            userArrayList.add(user);
        }
        return userArrayList;
    }

    public void deleteUser(int id_user){
        db_helper.queryData("DELETE FROM User WHERE Id= '"+id_user+"' ");
        db_helper.queryData("DELETE FROM Address WHERE IdUser='"+id_user+"' ");
        db_helper.queryData("DELETE FROM Card WHERE IdUser='"+id_user+"'");
    }

}
