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
import com.example.android_71221.Model.User;
import com.example.android_71221.Model.UserModel;
import com.example.android_71221.R;

public class UpdateProfileActivity extends AppCompatActivity {

    private Button btn_update_profile;
    private EditText edit_first,edit_last,edit_phone,edit_address,edit_province,edit_district;
    private final static String USER_INFO = "USER_INFO";
    private DB_Helper db_helper = new DB_Helper(this, "EmarketDB.sqlite", null, 1);
    private UserModel usermodel=new UserModel(db_helper);
    private User user;
    private ImageView backButton_product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        SharedPreferences mPrefs = getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        int id=mPrefs.getInt("id_user",0);
        initUI();

        user=usermodel.getUser(id);
        edit_first.setText(user.getFirstName());
        edit_last.setText(user.getLastName());
        edit_phone.setText(user.getPhoneNumber());

        btn_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String first_edit=edit_first.getText().toString().trim();
                    String last_edit=edit_last.getText().toString().trim();
                    String phone_edit=edit_phone.getText().toString().trim();

                    user.setFirstName(first_edit);
                    user.setLastName(last_edit);
                    user.setPhoneNumber(phone_edit);

                    usermodel.UpdateDataUser(user);


//                    Intent intent =new Intent(UpdateProfileActivity.this, MainActivity.class);
//                    String tr="true";
//                    intent.putExtra("currentFragment",tr);
//                    startActivity(intent);
                        finish();
            }
        });

        backButton_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void initUI(){
        edit_first=findViewById(R.id.update_firstname);
        edit_last=findViewById(R.id.update_lastname);
        edit_phone=findViewById(R.id.update_phone);
        btn_update_profile=findViewById(R.id.btn_update_profile);
        backButton_product = findViewById(R.id.backButton_product);
    }

}