package com.example.android_71221.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.android_71221.DB_Helper.DB_Helper;
import com.example.android_71221.Model.Address;
import com.example.android_71221.Model.UserModel;
import com.example.android_71221.R;

public class ShoppingAddressActivity extends AppCompatActivity {

    private Button btn_update_address;
    private EditText edit_address,edit_province,edit_district;
    private final static String USER_INFO = "USER_INFO";
    private DB_Helper db_helper = new DB_Helper(this, "EmarketDB.sqlite", null, 1);
    private UserModel usermodel=new UserModel(db_helper);
    private Address address;
    private ImageView backButton_product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_address);

        SharedPreferences mPrefs = getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        int id=mPrefs.getInt("id_user",0);
        initUI();

        address=usermodel.getAddress(id);
        edit_address.setText(address.getDetail());
        edit_province.setText(address.getProvince());
        edit_district.setText(address.getDistrict());

        btn_update_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String address_edit=edit_address.getText().toString().trim();
                String province_edit=edit_province.getText().toString().trim();
                String district_edit=edit_district.getText().toString().trim();

                address.setDetail(address_edit);
                address.setProvince(province_edit);
                address.setDistrict(district_edit);

                usermodel.UpdateDataAddress(address);

                Intent intent =new Intent(ShoppingAddressActivity.this, MainActivity.class);
                String tr="true";
                intent.putExtra("currentFragment",tr);
                startActivity(intent);

            }
        });

        backButton_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initUI(){
        edit_address=findViewById(R.id.update_address);
        edit_province=findViewById(R.id.update_province);
        edit_district=findViewById(R.id.update_district);
        btn_update_address=findViewById(R.id.btn_update_address);
        backButton_product = findViewById(R.id.backButton_product);
    }

}