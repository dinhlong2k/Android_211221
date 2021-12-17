package com.example.android_71221.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.android_71221.Adapter.BasketAdapter;
import com.example.android_71221.Controller.CheckoutActivity;
import com.example.android_71221.Controller.MainActivity;
import com.example.android_71221.Model.Basket;
import com.example.android_71221.Model.Product;
import com.example.android_71221.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class OrderFragment extends Fragment implements BasketAdapter.EventListener{
    private ListView basketListView;
    private BasketAdapter basketAdapter;
    private View view;
    private Context context;
    private ImageView sideMenuBtn;
    private TextView totalPrice;
    private LinearLayout basketBottomLayout;
    private ConstraintLayout notfoundLayout;
    private Button checkoutBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order, container, false);
        reference();

        clickEventListener();

        return view;

    }

    private void clickEventListener(){
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CheckoutActivity.class);
                startActivity(intent);
            }
        });

        sideMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).openNav();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("order fragment", String.valueOf(Basket.basket.size()));
        basketAdapter = new BasketAdapter(context, R.layout.item_basket, Basket.basket, this);
        basketListView.setAdapter(basketAdapter);
        setPrice();
    }

    public void reference() {
        basketListView = view.findViewById(R.id.basketListView);
        totalPrice = view.findViewById(R.id.totalPrice);
        basketBottomLayout = view.findViewById(R.id.bottomBasket);
        notfoundLayout = view.findViewById(R.id.notFoundContainer);
        checkoutBtn = view.findViewById(R.id.checkoutBtn);
        sideMenuBtn = view.findViewById(R.id.sideMenuBtn);


    }
    // update gia cho gio hang
    public void setPrice() {
        String price = "VND " + PreventNotationDouble(Basket.TotalPrice());
        totalPrice.setText(price);
        whichViewShow();
    }


    private String PreventNotationDouble(Double number){
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(340);
        String res = df.format(number);
        return res;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onEvent() {
        Log.d("event", "onEvent: clicked");
        setPrice();
    }

    private void whichViewShow(){
        if (Basket.basket.size() == 0){
            basketBottomLayout.setVisibility(View.GONE);
            basketListView.setVisibility(View.GONE);
            notfoundLayout.setVisibility(View.VISIBLE);
        }
        else{
            basketBottomLayout.setVisibility(View.VISIBLE);
            basketListView.setVisibility(View.VISIBLE);
            notfoundLayout.setVisibility(View.GONE);
        }
    }

}
