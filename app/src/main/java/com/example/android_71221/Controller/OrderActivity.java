package com.example.android_71221.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.android_71221.Admin.Adapter.PendingAdapter;
import com.example.android_71221.Admin.Model.Bill;
import com.example.android_71221.Admin.Model.Buy;
import com.example.android_71221.Admin.Model.PendingItem;
import com.example.android_71221.Admin.Model.Product_Qtt;
import com.example.android_71221.DB_Helper.DB_Helper;
import com.example.android_71221.Model.BillModel;
import com.example.android_71221.Model.BuyModel;
import com.example.android_71221.Model.Product;
import com.example.android_71221.Model.ProductModel;
import com.example.android_71221.Model.User;
import com.example.android_71221.Model.UserModel;
import com.example.android_71221.R;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    private ImageView backBtn;
    private ListView listView;
    private BillModel billModel;
    private DB_Helper db_helper;
    private PendingAdapter pendingAdapter;
    private ArrayList<PendingItem> pendingItemArrayList;
    ArrayList<Bill> billArrayListByID;
    private final static String USER_INFO= "USER_INFO";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        reference();
        getData();
        pendingAdapter = new PendingAdapter(this, R.layout.item_pending, pendingItemArrayList);
        listView.setAdapter(pendingAdapter);
        clickEventListener();
    }

    private void reference() {
        backBtn = findViewById(R.id.backButton_product);
        listView = findViewById(R.id.listViewHistory);
        db_helper =  new DB_Helper(this, "EmarketDB.sqlite", null, 1);
        billModel = new BillModel(db_helper);
        billArrayListByID = new ArrayList<Bill>();
    }

    private void getData(){
        SharedPreferences mPrefs = getSharedPreferences(USER_INFO,MODE_PRIVATE);
        int user_id= mPrefs.getInt("id_user",0);
        billArrayListByID = billModel.GetBillDataByID(user_id);
        pendingItemArrayList = billModel.PopulateDataFromBillID(billArrayListByID);
    }

    private void clickEventListener(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}