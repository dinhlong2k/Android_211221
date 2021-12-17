package com.example.android_71221.Admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android_71221.Admin.Model.Product_Qtt;
import com.example.android_71221.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListQttAdapter extends ArrayAdapter {
    private Context context;
    private int resource;
    private ArrayList<Product_Qtt> productQttArrayList;
    public ListQttAdapter(@NonNull Context context, int resource, ArrayList<Product_Qtt> productQttArrayList) {
        super(context, resource, productQttArrayList);
        this.context = context;
        this.resource = resource;
        this.productQttArrayList = productQttArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(resource, parent, false);
        Product_Qtt product_qtt = productQttArrayList.get(position);

        //reference
        TextView productName = view.findViewById(R.id.productName_pending);
        TextView productQtt = view.findViewById(R.id.qtt_pending);
        //reference

        productName.setText(product_qtt.getProductName());
        productQtt.setText(String.valueOf(product_qtt.getQtt()));

        return view;
    }
}
