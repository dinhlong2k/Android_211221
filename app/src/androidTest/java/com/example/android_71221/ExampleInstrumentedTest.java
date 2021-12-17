package com.example.android_71221;

import android.content.Context;
import android.database.Cursor;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.android_71221.DB_Helper.DB_Helper;
import com.example.android_71221.Model.Card;
import com.example.android_71221.Model.CardType;
import com.example.android_71221.Model.CardTypeModel;
import com.example.android_71221.Model.UserModel;

import java.util.ArrayList;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private DB_Helper db_helper;
    private CardTypeModel cardTypeModel;
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.android_71221", appContext.getPackageName());

    }


    @Test
    public void createCardType(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.android_71221", appContext.getPackageName());

        db_helper = new DB_Helper(appContext.getApplicationContext(), "EmarketDB.sqlite", null, 1);
        cardTypeModel=new CardTypeModel(db_helper);
        CardType cardType=new CardType("Visa");
        CardType cardType1=new CardType("MasterCard");
        CardType cardType2=new CardType("Local Bank");
        cardTypeModel.CreateTableCardsType();
        cardTypeModel.InsertCardType(cardType);
        cardTypeModel.InsertCardType(cardType1);
        cardTypeModel.InsertCardType(cardType2);
    }



    @Test
    public void listCardType(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.android_71221", appContext.getPackageName());

        db_helper = new DB_Helper(appContext.getApplicationContext(), "EmarketDB.sqlite", null, 1);
        cardTypeModel=new CardTypeModel(db_helper);
        ArrayList<CardType> listCard;
        listCard=cardTypeModel.getListCardType();

        ArrayList<String> listCard1=new ArrayList<String>();

        for (int i = 0 ; i< listCard.size();i++){
            //listCard1.add(listCard.get(i).getName().toString());
            System.out.println(listCard.get(i).getName());
        }
    }
}