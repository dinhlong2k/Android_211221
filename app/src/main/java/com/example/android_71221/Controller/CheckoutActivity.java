package com.example.android_71221.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.android_71221.DB_Helper.DB_Helper;
import com.example.android_71221.Model.Address;
import com.example.android_71221.Model.Basket;
import com.example.android_71221.Model.BillModel;
import com.example.android_71221.Model.BuyModel;
import com.example.android_71221.Model.Card;
import com.example.android_71221.Model.CardModel;
import com.example.android_71221.Model.CardType;
import com.example.android_71221.Model.Product;
import com.example.android_71221.Model.ProductModel;
import com.example.android_71221.Model.User;
import com.example.android_71221.Model.UserModel;
import com.example.android_71221.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CheckoutActivity extends AppCompatActivity {
    private TextView customerNameLabel;
    private TextView customerAddressLabel;
    private TextView customerPhoneLabel;
    private User user;
    private DB_Helper db_helper;
    private UserModel userModel;
    private Address address;
    private final static String USER_INFO= "USER_INFO";
    private TextView changeBtn;
    private TextView  totalPrice;
    private ImageView backButton_product;
    private CardModel cardModel;
    private Card card;
    private RadioButton localBank;
    private RadioButton visaBank;
    private RadioButton masterBank;
    private ArrayList<RadioButton> radioButtons;
    private Button checkoutBtn;
    private ProgressDialog progressDialog;
    private BuyModel buyModel;
    private BillModel billModel;
    private ProductModel productModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        reference();
        if (Basket.basket != null){
            String totalPriceTxt = " VND " + PreventNotationDouble(Basket.TotalPrice());
            totalPrice.setText(totalPriceTxt);
        }

        getDataUser();
        populateDataUser();
        clickEventListener();


    }

    private void reference() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Vui Lòng Đợi...");
        customerNameLabel = findViewById(R.id.nameLabel);
        customerAddressLabel = findViewById(R.id.addressLabel);
        customerPhoneLabel = findViewById(R.id.phoneLabel);
        changeBtn = findViewById(R.id.changeBtn);
        db_helper = new DB_Helper(CheckoutActivity.this, "EmarketDB.sqlite", null, 1);
        userModel = new UserModel(db_helper);
        cardModel = new CardModel(db_helper);
        totalPrice = findViewById(R.id.totalPrice);
        backButton_product = findViewById(R.id.backButton_product);
        localBank = findViewById(R.id.localNumber);
        masterBank = findViewById(R.id.masterNumber);
        visaBank = findViewById(R.id.visaNumber);

        radioButtons = new ArrayList<RadioButton>();
        radioButtons.add(visaBank);
        radioButtons.add(masterBank);
        radioButtons.add(localBank);

        checkoutBtn = findViewById(R.id.checkoutBtn);
        buyModel = new BuyModel(db_helper);
        billModel = new BillModel(db_helper);
        productModel = new ProductModel(db_helper);

        billModel.CreateTableBill();
        buyModel.CreateTableBuy();
//        billModel.DeleteBillTable();
//        buyModel.DeleteBuyTable();
    }

    private void getDataUser(){
        SharedPreferences mPrefs = getSharedPreferences(USER_INFO,MODE_PRIVATE);
        int user_id= mPrefs.getInt("id_user",0);
        user = userModel.getUser(user_id);
        address = userModel.getAddress(user_id);
        card = cardModel.getCard(user_id);


    }

    private void populateDataUser(){
        customerNameLabel.setText(user.getFullName());
        customerPhoneLabel.setText(user.getPhoneNumber());
        customerAddressLabel.setText(address.getAddressUser());
        Log.d("Card", "populateDataUser: "+String.valueOf(card.getCardType()));
        // card infor
        for (int i = 0 ; i < radioButtons.size(); i++){
            if (i + 1 == card.getCardType()){
                radioButtons.get(i).setEnabled(true);
                radioButtons.get(i).setText(card.getNumber());
            }
            else{
                radioButtons.get(i).setEnabled(false);
                radioButtons.get(i).setText("Không Tìm Thấy Thông Tin");
            }
        }
    }

    private void clickEventListener(){
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null && address != null){
                    clickBottomSheetChangingInfor(user, address);
                }

            }
        });


        backButton_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetConfirm(card);
            }
        });
    }

    private void showBottomSheetConfirm(Card card){
        View viewDialog = getLayoutInflater().inflate(R.layout.item_bottom_confirm_pay, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(viewDialog);

        bottomSheetDialog.show();
        TextView productQtt = viewDialog.findViewById(R.id.productQtt);
        TextView cardNumber = viewDialog.findViewById(R.id.cardNumber);
        String encriptNumber = "**** **** **** ";
        Button checkoutBtnBottomSheet = viewDialog.findViewById(R.id.buttonCheckout);
        for (int i = 0 ; i < card.getNumber().length();i++){
            if (i >= card.getNumber().length() - 4){
                encriptNumber += card.getNumber().charAt(i);
            }
        }
        TextView cardName = viewDialog.findViewById(R.id.cardName);
        TextView cardDate = viewDialog.findViewById(R.id.cardDate);
        TextView totalPriceBottom = viewDialog.findViewById(R.id.totalPriceBottom);

        if (Basket.basket != null){
            productQtt.setText(String.valueOf(Basket.basket.size()));
            String totalPriceTxt = " VND " + PreventNotationDouble(Basket.TotalPrice());
            totalPriceBottom.setText(totalPriceTxt);
        }

        cardNumber.setText(encriptNumber);
        cardName.setText(card.getName());
        cardDate.setText(card.getExp());

        checkoutBtnBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (UpdateBillDB()){
                    // delay
                    if (UpdateBuyDB()){
                        progressDialog.show();
                        bottomSheetDialog.dismiss();
                        progressDialog.dismiss();
                        Intent intent = new Intent(CheckoutActivity.this, CompletelyActivity.class);
                        startActivity(intent);
                    }

                }
            }
        });

    }

    private void clickBottomSheetChangingInfor(User user, Address address){
        View viewDialog = getLayoutInflater().inflate(R.layout.item_bottom_change_infor, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
        bottomSheetDialog.setContentView(viewDialog);

        bottomSheetDialog.show();

        Button cancelBtn = viewDialog.findViewById(R.id.cancelBtn);
        Button addBtn = viewDialog.findViewById(R.id.addBtn);
        EditText firstName = viewDialog.findViewById(R.id.edit_first);
        EditText lastName = viewDialog.findViewById(R.id.edit_last);
        EditText phoneNumber = viewDialog.findViewById(R.id.edit_phone);
        EditText province = viewDialog.findViewById(R.id.edit_province);
        EditText district = viewDialog.findViewById(R.id.edit_district);
        EditText detail = viewDialog.findViewById(R.id.edit_detail);

        // set default data

        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        phoneNumber.setText(user.getPhoneNumber());
        province.setText(address.getProvince());
        district.setText(address.getDistrict());
        detail.setText(address.getDetail());

        // click event
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomSheetDialog.dismiss();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = firstName.getText().toString().trim();
                String lastname= lastName.getText().toString().trim();
                String phone = phoneNumber.getText().toString().trim();
                String provinceTxt = province.getText().toString().trim();
                String districtTxt = district.getText().toString().trim();
                String detailTxt = detail.getText().toString().trim();

                user.setFirstName(firstname);
                user.setLastName(lastname);
                user.setPhoneNumber(phone);

                address.setDetail(detailTxt);
                address.setDistrict(districtTxt);
                address.setProvince(provinceTxt);

                userModel.UpdateDataUser(user);
                userModel.UpdateDataAddress(address);

                bottomSheetDialog.dismiss();
                populateDataUser();
            }
        });
    }

    private String PreventNotationDouble(Double number){
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(340);
        String res = df.format(number);
        return res;
    }

    private Boolean UpdateBillDB(){

        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        billModel.InsertDataBill(timeStamp, user.getId(),Basket.TotalPrice());
        return true;
    }

    private Boolean UpdateBuyDB(){

        // detect ID of the lastest row
        int idBill = billModel.FindLastestBill();
        for (int i = 0 ; i < Basket.basket.size();i++){
            Product product = Basket.basket.get(i);
            buyModel.InsertDataBuy(idBill, product);
            // subtract the total quatity = bought qtt
            productModel.UpdateProductQTT(product);
        }
        return true;
    }

}