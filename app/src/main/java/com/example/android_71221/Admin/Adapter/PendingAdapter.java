package com.example.android_71221.Admin.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android_71221.Admin.Model.PendingItem;
import com.example.android_71221.Admin.Model.Product_Qtt;
import com.example.android_71221.R;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class PendingAdapter extends ArrayAdapter {
    private Context context;
    private int resource;
    private ArrayList<PendingItem> pendingItemArrayList;
    public PendingAdapter(@NonNull Context context, int resource, ArrayList<PendingItem> pendingItemArrayList) {
        super(context, resource, pendingItemArrayList);
        this.context = context;
        this.resource = resource;
        this.pendingItemArrayList = pendingItemArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(resource, parent, false);
        PendingItem pendingItem = pendingItemArrayList.get(position);
        //reference
        TextView idBill = view.findViewById(R.id.idBill);
        TextView customerName = view.findViewById(R.id.customer_name);
        TextView dateBill = view.findViewById(R.id.date_bill);
        TextView priceBill = view.findViewById(R.id.total_bill);
        ListView listView = view.findViewById(R.id.listView_pending_qtt);
        //reference

        // set data
        String id = "#" + String.valueOf(pendingItem.getIdBill());
        idBill.setText(id);
        customerName.setText(pendingItem.getCustomerName());
        dateBill.setText(pendingItem.getTime());
        String price = PreventNotationDouble(pendingItem.getPrice()) + " VND";
        priceBill.setText(price);
        Log.d("itemArrayList", String.valueOf(pendingItem.getProductQttArrayList()));
        ArrayList<Product_Qtt> productQttArrayList = new ArrayList<Product_Qtt>();
        productQttArrayList = pendingItem.getProductQttArrayList();
        ListQttAdapter listQttAdapter = new ListQttAdapter(getContext(),R.layout.item_product_qtt_pending ,productQttArrayList);

        //set height dynamic
        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) listView.getLayoutParams();
        int items = pendingItem.getProductQttArrayList().size();
        lp.height = 110 * items;
        listView.setLayoutParams(lp);
        //set height dynamic


        listView.setAdapter(listQttAdapter);
        return view;
    }

    private String PreventNotationDouble(Double number){
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(340);
        String res = df.format(number);
        return res;
    }
}
