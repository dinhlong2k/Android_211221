package com.example.android_71221.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android_71221.DB_Helper.DB_Helper;
import com.example.android_71221.Model.Address;
import com.example.android_71221.Model.Card;
import com.example.android_71221.Model.CardModel;
import com.example.android_71221.Model.CategoryModel;
import com.example.android_71221.Model.User;
import com.example.android_71221.Model.UserModel;
import com.example.android_71221.R;

public class RegisterActivity extends AppCompatActivity {

    EditText edit_email,edit_pass,edit_confirmpass,edit_first,edit_last;
    TextView txt_lg;
    Button btn_login;
    private DB_Helper db_helper = new DB_Helper(this, "EmarketDB.sqlite", null, 1);
    private UserModel usermodel=new UserModel(db_helper);
    private CardModel cardModel=new CardModel(db_helper);
    private CategoryModel categoryModel=new CategoryModel(db_helper);
    String email,confirmPass,pass,firstName,lastName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initUI();
        redirectLogin();
        usermodel.CreateTableUser();
        usermodel.CreateTableAddress();
        cardModel.CreateTableCard();
        categoryModel.CreateTableCategory();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validateEmail() && validatePassword() && checkEmpty()){
                    register();

                }
            }
        });
    }

    private void initUI(){
        edit_email=findViewById(R.id.edit_mail);
        edit_pass =findViewById(R.id.edit_pass);
        edit_confirmpass=findViewById(R.id.edit_confirmpass);
        edit_first=findViewById(R.id.edit_first);
        edit_last=findViewById(R.id.edit_last);
        btn_login=findViewById(R.id.signupBtn);
        txt_lg=findViewById(R.id.haveAccountYet);
    }

    private void getT(){
        email =edit_email.getText().toString().trim();
        pass = edit_pass.getText().toString().trim();
        confirmPass = edit_confirmpass.getText().toString().trim();
        firstName=edit_first.getText().toString().trim();
        lastName=edit_last.getText().toString().trim();
    }

    private Boolean validateEmail(){
        email =edit_email.getText().toString().trim();
        if(email.isEmpty()){
            edit_email.setError("Field can't be empty");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edit_email.setError("Please Enter a valid email address");
            return false;
        }
        else if(usermodel.checkEmailExits(email)){
            edit_email.setError("Email already exits");
            return false;
        }
        return true;
    }

    private Boolean validatePassword() {
        pass = edit_pass.getText().toString().trim();
        confirmPass = edit_confirmpass.getText().toString().trim();
        if (pass.isEmpty()) {
            edit_pass.setError("Field can't be empty");
            return false;
        }
        if (pass.length() < 5) {
            edit_pass.setError("Password must be at least 5 characters");
            return false;
        }
        if (!pass.equals(confirmPass)) {
            edit_confirmpass.setError("Password Would Not be matched");
            return false;
        }
        return true;
    }

    private Boolean checkEmpty(){
        String firstName=edit_first.getText().toString().trim();
        String lastName=edit_last.getText().toString().trim();

        if(firstName.isEmpty()){
            edit_first.setError("Field can't be empty");
            return false;
        }
        if(lastName.isEmpty()){
            edit_last.setError("Field can't be empty");
            return false;
        }
        else return true;
    }

    private void redirectLogin(){
        txt_lg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void register(){
        getT();
        int role=1;
        User userRegis=new User(email,pass,firstName,lastName,role);
        usermodel.InsertDataUser(userRegis);
        Address address=new Address();
        Card card=new Card();
        User user_address=usermodel.login(email,pass);
        usermodel.InsertDataAddress(address,user_address.getId());
        cardModel.InsertDataCard(card,user_address.getId());
//        Gson gson=new Gson();
//        String myJson=gson.toJson(userRegis);
        Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
//        intent.putExtra("myjson",myJson);
        startActivity(intent);
    }
}