package com.example.android_71221.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.android_71221.Controller.CardActivity;
import com.example.android_71221.Controller.MainActivity;
import com.example.android_71221.Controller.OrderActivity;
import com.example.android_71221.Controller.ShoppingAddressActivity;
import com.example.android_71221.Controller.UpdateProfileActivity;
import com.example.android_71221.DB_Helper.DB_Helper;
import com.example.android_71221.Model.Address;
import com.example.android_71221.Model.User;
import com.example.android_71221.Model.UserModel;
import com.example.android_71221.R;
import com.google.gson.Gson;

public class ProfileFragment extends Fragment {
    private View view;
    private TextView tv_name,tv_address;
    private CardView card_edit,card_address,card_card,card_orderHistory;
    private final static String USER_INFO = "USER_INFO";
    private ImageView image_location;
    private ImageView sideMenuBtn;

    private User user;
    private Address address;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        Reference();
        EventCard();
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        DB_Helper db_helper = new DB_Helper(context, "EmarketDB.sqlite", null, 1);
        UserModel usermodel=new UserModel(db_helper);

        SharedPreferences mPrefs = this.getActivity().getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
//        Gson gson=new Gson();
//        String json=mPrefs.getString("myjson",null);
//        user=gson.fromJson(json,User.class);
        int user_id=mPrefs.getInt("id_user",0);

        user=usermodel.getUser(user_id);
        address= usermodel.getAddress(user_id);

    }

    public void Reference(){
        sideMenuBtn = view.findViewById(R.id.sideMenuBtn);
        tv_name=(TextView) view.findViewById(R.id.name_profile);
        tv_address=(TextView) view.findViewById(R.id.address_profile);
        card_edit=(CardView) view.findViewById(R.id.edit_profile);
        card_address=(CardView) view.findViewById(R.id.shopping_address);
        card_card=(CardView) view.findViewById(R.id.Cards);
        card_orderHistory = view.findViewById(R.id.order_history);
        image_location=(ImageView) view.findViewById(R.id.image_location_profile);
        tv_name.setText(user.getFullName());

        if(address.getAddressUser().equals("")){
            image_location.setVisibility(View.INVISIBLE);
        }else{
            tv_address.setText(address.getAddressUser());
            image_location.setVisibility(View.VISIBLE);
        }
    }

    private void EventCard(){

        sideMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).openNav();
            }
        });

        card_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity().getBaseContext(), UpdateProfileActivity.class);
                startActivity(intent);
            }
        });


        card_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity().getBaseContext(), ShoppingAddressActivity.class);
                startActivity(intent);
            }
        });

        card_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity().getBaseContext(), CardActivity.class);
                startActivity(intent);
            }
        });

        card_orderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity().getBaseContext(), OrderActivity.class);
                startActivity(intent);
            }
        });

    }
}
