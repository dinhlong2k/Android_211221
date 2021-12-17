package com.example.android_71221.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.android_71221.DB_Helper.DB_Helper;
import com.example.android_71221.Model.Card;
import com.example.android_71221.Model.CardModel;
import com.example.android_71221.Model.CardType;
import com.example.android_71221.Model.CardTypeModel;
import com.example.android_71221.Model.UserModel;
import com.example.android_71221.R;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

public class CardActivity extends AppCompatActivity {

    private DB_Helper db_helper = new DB_Helper(this, "EmarketDB.sqlite", null, 1);
    private UserModel usermodel=new UserModel(db_helper);
    private ImageView backButton_product;

    private CardTypeModel cardTypeModel=new CardTypeModel(db_helper);

    private CardModel cardModel=new CardModel(db_helper);
    private Card card;
    private final static String USER_INFO = "USER_INFO";

    private Spinner dropdown;
    private EditText edit_name,edit_cardNumber,edit_expCard,edit_CVV;
    private Button btnUpdateCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        initUI();

        ArrayList<CardType> listCard;
        listCard=cardTypeModel.getListCardType();

        ArrayList<String> listCard1=new ArrayList<String>();

        for (int i = 0 ; i< listCard.size();i++){
            listCard1.add(listCard.get(i).getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_dropdown_item, listCard1);
        dropdown.setAdapter(adapter);


        SharedPreferences mPrefs = getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        int id=mPrefs.getInt("id_user",0);

        card=cardModel.getCard(id);
        edit_name.setText(card.getName());
        edit_cardNumber.setText(card.getNumber());
        edit_CVV.setText(card.getCvv());
        edit_expCard.setText(card.getExp());
        dropdown.setSelection(card.getCardType() -1);

        validateForm();
        eventDate();

        btnUpdateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateForm()){
                    int cardType_edit= (int) dropdown.getSelectedItemId();
                    String name_edit=edit_name.getText().toString().trim();
                    String cardNumber_edit=edit_cardNumber.getText().toString().trim();
                    String expCard=edit_expCard.getText().toString().trim();
                    String CVV_edit=edit_CVV.getText().toString().trim();
                    name_edit=name_edit.toUpperCase();
                    cardType_edit+=1;

                    card.setName(name_edit);
                    card.setNumber(cardNumber_edit);
                    card.setExp(expCard);
                    card.setCardType(cardType_edit);
                    card.setCvv(CVV_edit);

                    cardModel.UpdateDataCard(card,id);

//                    Intent intent =new Intent(CardActivity.this, MainActivity.class);
//                    String tr="true";
//                    intent.putExtra("currentFragment",tr);
//                    startActivity(intent);
                    finish();
                }
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
        dropdown=(Spinner) findViewById(R.id.spinnerCardType);
        edit_name=findViewById(R.id.update_cardName);
        edit_cardNumber=findViewById(R.id.update_cardNumber);
        edit_expCard=findViewById(R.id.update_expCard);
        edit_CVV=findViewById(R.id.update_cvv);
        btnUpdateCard=findViewById(R.id.btn_updateCard);
        backButton_product = findViewById(R.id.backButton_product);
    }

    private void eventDate(){
        edit_expCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar today=Calendar.getInstance();

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(CardActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                edit_expCard.setText((selectedMonth+1) +"/"+ selectedYear);
                                }
                            }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

                builder.setActivatedMonth(Calendar.JULY)
                        .setMinYear(1990)
                        .setActivatedYear(today.get(Calendar.YEAR))
                        .setMaxYear(2030)
                        .setTitle("Select month")
                        .build()
                        .show();

            }
        });
    }

    private boolean validateForm(){
        String cardType_edit=dropdown.getSelectedItem().toString();
        String name_edit=edit_name.getText().toString().trim();
        String cardNumber_edit=edit_cardNumber.getText().toString().trim();
        String expCard=edit_expCard.getText().toString().trim();
        String CVV_edit=edit_CVV.getText().toString().trim();

        if(name_edit.isEmpty()){
            edit_name.setError("Field can't be empty");
            return false;
        }
        if(cardNumber_edit.isEmpty()){
            edit_cardNumber.setError("Field can't be empty");
            return false;
        }
        if(expCard.isEmpty()){
            edit_expCard.setError("Field can't be empty");
            return false;
        }
        if(CVV_edit.isEmpty()){
            edit_CVV.setError("Field can't be empty");
            return false;
        }
        return true;

    }

}