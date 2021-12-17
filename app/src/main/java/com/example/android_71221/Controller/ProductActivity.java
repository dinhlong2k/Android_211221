package com.example.android_71221.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android_71221.Adapter.SliderAdapter;
import com.example.android_71221.DB_Helper.DB_Helper;
import com.example.android_71221.Fragment.FavoriteFragment;
import com.example.android_71221.Model.Basket;
import com.example.android_71221.Model.FavoriteModel;
import com.example.android_71221.Model.Product;
import com.example.android_71221.R;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class ProductActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private Handler sliderHandler = new Handler();
    private ArrayList<String> imgURL;
    private SliderAdapter sliderAdapter;
    private ImageView heart_button;
    private TextView productLabel;
    private TextView productDescript;
    private TextView productPrice;
    private ImageView backBtn;
    private ImageView likeBtn;
    private Product product;
    private Button addBasketBtn;
    private Boolean isLiked;
    private DB_Helper db_helper;
    private FavoriteModel favoriteModel;
    private int idUser = 0;
    private final static String USER_INFO= "USER_INFO";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        SharedPreferences mPrefs = getSharedPreferences(USER_INFO,MODE_PRIVATE);
        idUser = mPrefs.getInt("id_user",0);
        reference();
        choseHeartColor();

        // create slider adapter
        viewPager2.setAdapter(new SliderAdapter(product.getImages(), viewPager2));

        setData();
        clickEvents();

    }

    private void setData(){
        productLabel.setText(product.getName());
        productDescript.setText(product.getDescription());
        String price = PreventNotationDouble(product.getPrice()) + " VND";
        productPrice.setText(price);
    }

    private void clickEvents(){
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addBasketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // check product added , not add twice
                if (!checkProductExisted(product)){
                    // set ql default = 1
                    product.setQuatity(1);
                    Basket.basket.add(product);
                    Toast.makeText(ProductActivity.this, "Thêm Vào Giỏ Hàng Thành Công", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ProductActivity.this, "Bạn Đã Thêm Sản Phẩm Này", Toast.LENGTH_SHORT).show();
                }

            }
        });

        heart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isLiked){
                    heart_button.setImageResource(R.drawable.heartred);
                    favoriteModel.InsertFavoriteData(idUser, product.getId());
                    isLiked = true;
                }
                else{
                    heart_button.setImageResource(R.drawable.ic_heart);
                    favoriteModel.DeleteFavoriteData(idUser, product.getId());
                    isLiked = false;
                }
            }
        });
    }

    private void choseHeartColor(){
        Gson gson = new Gson();
        product = gson.fromJson(getIntent().getStringExtra("productJson"), Product.class);
        isLiked = favoriteModel.IsFavorite(idUser, product.getId());
        Log.d("chosen", "choseHeartColor: "+ String.valueOf(isLiked));
        if (isLiked){
            heart_button.setImageResource(R.drawable.heartred);

        }
        else{
            heart_button.setImageResource(R.drawable.ic_heart);
        }
    }

    private void reference() {
        isLiked = false;
        db_helper = new DB_Helper(ProductActivity.this, "EmarketDB.sqlite", null, 1);
        favoriteModel = new FavoriteModel(db_helper);
        productLabel = findViewById(R.id.productLabel);
        productDescript = findViewById(R.id.descripReg);
        productPrice = findViewById(R.id.product_price);
        backBtn = findViewById(R.id.backButton_product);
        heart_button = findViewById(R.id.heart_button);
        heart_button.setBackgroundColor(Color.rgb(100,100,50));
        addBasketBtn = findViewById(R.id.addBasketBtn);
        viewPager2 = findViewById(R.id.viewPagerImageSlider);
        imgURL = new ArrayList<String>();
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000); // slider duration

            }
        });
        // which hear color is show

    }
    private  Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    private String PreventNotationDouble(Double number){
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(340);
        String res = df.format(number);
        return res;
    }

    private boolean checkProductExisted(Product product){
        int id = product.getId();
        for (int i = 0 ; i < Basket.getBasket().size();i++){
            Product bought = Basket.getBasket().get(i);
            if (id == bought.getId()){
                return true;
            }
        }
        return false;
    }

    public interface EventListener {
        void onEvent();
    }
}