package com.example.android_71221;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.database.Cursor;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.android_71221.DB_Helper.DB_Helper;
import com.example.android_71221.Model.Card;
import com.example.android_71221.Model.CardModel;
import com.example.android_71221.Model.CardTypeModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import java.util.ArrayList;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
@RunWith(AndroidJUnit4.class)
public class TestCard {

    private DB_Helper db_helper;
    private CardModel cardModel;

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.android_71221", appContext.getPackageName());

    }

    @Test
    public void getCard(){

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.android_71221", appContext.getPackageName());

        db_helper = new DB_Helper(appContext.getApplicationContext(), "EmarketDB.sqlite", null, 1);
        cardModel=new CardModel(db_helper);

        ArrayList<Card> AddressArraylist = new ArrayList<Card>();
        int id_user=1;
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
    }


}
