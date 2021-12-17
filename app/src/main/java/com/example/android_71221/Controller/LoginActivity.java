package com.example.android_71221.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android_71221.Admin.AdminActivity.AdminActivity;
import com.example.android_71221.DB_Helper.DB_Helper;
import com.example.android_71221.Model.SessionManagement;
import com.example.android_71221.Model.User;
import com.example.android_71221.Model.UserModel;
import com.example.android_71221.R;

public class LoginActivity extends AppCompatActivity {

    EditText login_mail,login_pass;
    Button btnLogin;
    TextView tv_create,tv_error;
    String email,pass;
    private final static String USER_INFO= "USER_INFO";
    private DB_Helper db_helper = new DB_Helper(this, "EmarketDB.sqlite", null, 1);
    private UserModel usermodel=new UserModel(db_helper);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI();
        getT();
        clickRedirectSignUp();
        Login();
        checkSession();
    }

    private void initUI(){
        tv_create=findViewById(R.id.createAccountLabel);
        login_mail=findViewById(R.id.lg_email);
        login_pass=findViewById(R.id.lg_pass);
        btnLogin=findViewById(R.id.loginBtn);
        tv_error=findViewById(R.id.err_lg);
    }

    private void getT(){
        email =login_mail.getText().toString().trim();
        pass = login_pass.getText().toString().trim();
    }

    private void clickRedirectSignUp(){
        tv_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void checkSession() {
        //check if user is logged in
        //if user is logged in --> move to mainActivity

        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
        int userID = sessionManagement.getSession();

        User user=usermodel.getUsersession(userID);

        if(userID != -1){

            if(user.getRole() == 1) {
                moveToMainActivity();
            }
            else if(user.getRole() ==0){
                movetoAdminActivity();
            }
        }
        else{
            //do nothing
        }
    }

    private void movetoAdminActivity() {
        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void moveToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void Login(){
        //User user_lg=userModel.login(email,pass);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getT();
                User userlg= usermodel.login(email,pass);
                if(userlg != null){
                    SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
                    sessionManagement.saveSession(userlg);

                    //2. step
                    if(userlg.getRole()==1){
                        moveToMainActivity();
                    }
                    else if(userlg.getRole()==0){
                        movetoAdminActivity();
                    }

                    SharedPreferences mPrefs = getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor=mPrefs.edit();

//                    Gson gson=new Gson();
//                    String myJson=gson.toJson(userlg);
//
//                    editor.putString("myjson",myJson);

                    editor.putInt("id_user",userlg.getId());
                    editor.commit();
                    if (userlg.getRole() == 0){
                        Intent intent=new Intent(LoginActivity.this, AdminActivity.class);
                        //intent.putExtra("myjson",myJson);
                        startActivity(intent);
                    }
                    else{
                        Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                        //intent.putExtra("myjson",myJson);

                        startActivity(intent);
                    }

                }else{
                    tv_error.setText("Email or Password wrong");
                }

            }
        });
    }
}