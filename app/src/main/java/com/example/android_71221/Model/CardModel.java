package com.example.android_71221.Model;

import android.database.Cursor;

import com.example.android_71221.DB_Helper.DB_Helper;

import java.util.ArrayList;

public class CardModel {

    private DB_Helper db_helper;

    public CardModel(DB_Helper db_helper) {
        this.db_helper = db_helper;
    }

    public DB_Helper getDb_helper() {
        return db_helper;
    }

    public void setDb_helper(DB_Helper db_helper) {
        this.db_helper = db_helper;
    }

    //Card Query

    public void CreateTableCard(){
        db_helper.queryData("CREATE TABLE IF NOT EXISTS Card(Id INTEGER PRIMARY KEY AUTOINCREMENT, Name VARCHAR(255), " +
                " CardNumber VARCHAR(255), CVV VARCHAR(255), Exp VARCHAR(255),IdUser REFERENCES User(Id), IdTypeCard REFERENCES CardType(Id)  )");
    }

    public void InsertDataCard(Card card, int userId){
        db_helper.queryData("INSERT INTO Card VALUES(NULL,NULL,NULL,NULL,NULL,'"+userId+"',1)");
    }

    public void UpdateDataCard(Card card,int id_user){
        db_helper.queryData("UPDATE Card SET Name = '"+ card.getName()+"', CardNumber = '"+ card.getNumber()+"'," +
                "CVV = '"+card.getCvv()+"',Exp ='"+card.getExp()+"',IdTypeCard='"+card.getCardType()+"' ");
    }

    public Card getCard(int id_user){
        ArrayList<Card> AddressArraylist = new ArrayList<Card>();
        Cursor cardsData = db_helper.getData("SELECT * FROM Card WHERE IdUser= '"+id_user+"'");

        if(cardsData.moveToFirst()){
            int id1 = cardsData.getInt(0);
            String name = cardsData.getString(1);
            String number = cardsData.getString(2);
            String cvv = cardsData.getString(3);
            String exp =cardsData.getString(4);
            int id_CardType=cardsData.getInt(6);
            Card card=new Card(id1,name,number,cvv,exp,id_CardType);
            System.out.println(card.toString());
            AddressArraylist.add(card);
        }
        if(AddressArraylist.size()>0) return AddressArraylist.get(0);
        else return null;
    }
}
