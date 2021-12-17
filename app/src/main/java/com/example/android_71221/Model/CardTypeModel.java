package com.example.android_71221.Model;

import android.database.Cursor;

import com.example.android_71221.DB_Helper.DB_Helper;

import java.util.ArrayList;

public class CardTypeModel {

    private DB_Helper db_helper;

    public CardTypeModel(DB_Helper db_helper) {
        this.db_helper = db_helper;
    }

    public DB_Helper getDb_helper() {
        return db_helper;
    }

    public void setDb_helper(DB_Helper db_helper) {
        this.db_helper = db_helper;
    }

    //Card Type Query

    public void CreateTableCardsType(){
        db_helper.queryData("CREATE TABLE IF NOT EXISTS CardType(Id INTEGER PRIMARY KEY AUTOINCREMENT, CardType VARCHAR(255))");
    }

    public void InsertCardType(CardType cardType){
        db_helper.queryData("INSERT INTO CardType VALUES(NULL,'"+cardType.getName()+"')");
    }

    public ArrayList<CardType> getListCardType(){
        ArrayList<CardType> cardTypeArrayList = new ArrayList<CardType>();
        Cursor dataCardType = db_helper.getData("SELECT * FROM CardType");

        while (dataCardType.moveToNext()){
            int id = dataCardType.getInt(0);
            String cardName = dataCardType.getString(1);
            CardType cardType=new CardType(id,cardName);
            cardTypeArrayList.add(cardType);
        }
        return cardTypeArrayList;
    }

}
