package com.example.android_71221.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.android_71221.Fragment.HomeFragment;
import com.example.android_71221.Model.Basket;
import com.example.android_71221.R;

public class CompletelyActivity extends AppCompatActivity {
    
    private Button continueShopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completely);

        reference();
        clickEventListner();
        
    }

    private void clickEventListner() {
        continueShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Basket.basket.clear();
                Intent intent = new Intent(CompletelyActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void reference() {
        continueShopping = findViewById(R.id.continueShopping);
    }


}