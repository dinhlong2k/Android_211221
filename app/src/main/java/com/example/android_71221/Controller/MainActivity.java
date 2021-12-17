package com.example.android_71221.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android_71221.Adapter.ViewPagerAdapter;
import com.example.android_71221.DB_Helper.DB_Helper;
import com.example.android_71221.Model.Basket;
import com.example.android_71221.Model.CategoryModel;
import com.example.android_71221.Model.Product;
import com.example.android_71221.Model.SessionManagement;
import com.example.android_71221.Model.User;
import com.example.android_71221.Model.UserModel;
import com.example.android_71221.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private ViewPager2 viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private DB_Helper db_helper = new DB_Helper(this, "EmarketDB.sqlite", null, 1);
    private CategoryModel categoryModel = new CategoryModel(db_helper);
//    private OnObjectReceivedListener onObjectReceivedListener;
    private UserModel usermodel=new UserModel(db_helper);
    private String currentFrag="false";
    private final static String USER_INFO= "USER_INFO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reference();

        // catch event whenever click each fragment in navigationview
        navigationView.setNavigationItemSelectedListener(MainActivity.this);

        // catch event whenever click each fragment in navigationBottomView
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.action_fav:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.action_profile:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.action_order:
                        viewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.action_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.action_fav).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.action_profile).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.action_order).setChecked(true);
                        break;
                }
            }
        });
        //viewPager set Adapter
        viewPager.setAdapter(viewPagerAdapter);
        eventProfile();

        // Create basket

    }

    private void reference() {
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        viewPager = findViewById(R.id.view_pager);
        viewPagerAdapter = new ViewPagerAdapter(this);

        SharedPreferences mPrefs = getSharedPreferences(USER_INFO,MODE_PRIVATE);
//        Gson gson=new Gson();
//        String json=mPrefs.getString("id_user",null);
//        User user=gson.fromJson(json,User.class);

        int user_id=mPrefs.getInt("id_user",0);
        Basket.idUser = user_id;
        Basket.basket = new ArrayList<Product>();
        usermodel.CreateTableAddress();

        View nav_header=navigationView.getHeaderView(0);


        User user=usermodel.getUser(user_id);
        TextView tv_name=nav_header.findViewById(R.id.header_name);
        TextView tv_email=nav_header.findViewById(R.id.header_email);
        tv_name.setText(user.getFullName());
        tv_email.setText(user.getEmail());

        // catch event when clicking to open & close navigation drawer
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.nav_drawer_open, R.string.nav_drawer_close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.nav_home:
                mDrawerLayout.closeDrawer(GravityCompat.START, true);
                viewPager.setCurrentItem(0);
                break;
            case R.id.nav_profile:
                mDrawerLayout.closeDrawer(GravityCompat.START, true);
                viewPager.setCurrentItem(2);
                break;
            case R.id.nav_order:
                mDrawerLayout.closeDrawer(GravityCompat.START, true);
                viewPager.setCurrentItem(3);
                break;
            case R.id.nav_fav:
                mDrawerLayout.closeDrawer(GravityCompat.START, true);
                viewPager.setCurrentItem(1);
                break;

            case R.id.nav_logout:
                logout();
                Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    // detect when back btn in device is pressed
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START, true);
        }
        // drawer close & escape app
        else{
            super.onBackPressed();
        }
    }
    public void openNav(){
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerLayout.openDrawer(Gravity.LEFT, true);
    }
//    public interface OnObjectReceivedListener {
//        void onObjectReceived(CategoryModel categoryModel);
//    }
//    public void setAboutObjectListener(OnObjectReceivedListener listener) {
//        this.onObjectReceivedListener = listener;
//    }

    public void eventProfile(){
        Intent intent= getIntent();
        currentFrag=intent.getStringExtra("currentFragment");
        String tr="true";
        if(tr.equals(currentFrag)){
            viewPager.setCurrentItem(2);
            currentFrag="false";
        }
    }

    public void logout() {
        //this method will remove session and open login screen
        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        sessionManagement.removeSession();

        moveToLogin();
    }

    private void moveToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}