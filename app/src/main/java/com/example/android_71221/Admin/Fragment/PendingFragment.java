package com.example.android_71221.Admin.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android_71221.Admin.Adapter.PendingAdapter;
import com.example.android_71221.Admin.AdminActivity.AdminActivity;
import com.example.android_71221.Admin.Model.Bill;
import com.example.android_71221.Admin.Model.Buy;
import com.example.android_71221.Admin.Model.PendingItem;
import com.example.android_71221.Admin.Model.Product_Qtt;
import com.example.android_71221.DB_Helper.DB_Helper;
import com.example.android_71221.Model.BillModel;
import com.example.android_71221.Model.BuyModel;
import com.example.android_71221.Model.Product;
import com.example.android_71221.Model.ProductModel;
import com.example.android_71221.Model.User;
import com.example.android_71221.Model.UserModel;
import com.example.android_71221.R;

import java.util.ArrayList;

public class PendingFragment extends Fragment {
    private View view;
    private ListView listViewPending;
    private PendingAdapter pendingAdapter;
    private ArrayList<PendingItem> pendingItemArrayList;
    private ImageView sideMenu;
    private Context context;
    private BillModel billModel;
    private DB_Helper db_helper;
    ArrayList<Bill> billArrayList;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_pending, container, false);
        reference();
        // check data
        getData();

        pendingAdapter = new PendingAdapter(getContext(), R.layout.item_pending, pendingItemArrayList);
        listViewPending.setAdapter(pendingAdapter);

        // side Menu button click event
        sideMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("", "onClick: ");
                ((AdminActivity)getActivity()).openNav();
            }
        });

        return view;
    }

    private void reference() {
        listViewPending = view.findViewById(R.id.listViewPending);
        pendingItemArrayList = new ArrayList<PendingItem>();
        billArrayList = new ArrayList<Bill>();
        sideMenu = view.findViewById(R.id.admin_menu_nav);
        db_helper =  new DB_Helper(context, "EmarketDB.sqlite", null, 1);
        billModel = new BillModel(db_helper);

    }

    private void getData(){
        billArrayList = billModel.GetBillData();
        pendingItemArrayList = billModel.PopulateDataFromBillID(billArrayList);
    }


}