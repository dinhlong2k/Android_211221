package com.example.android_71221.Admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_71221.Model.Stock;
import com.example.android_71221.R;

import java.util.ArrayList;

public class StockAnalysAdapter extends RecyclerView.Adapter<StockAnalysAdapter.StockAnalysViewHolder> {
    private ArrayList<Stock> stockArrayList;
    private Context context;

    public StockAnalysAdapter(ArrayList<Stock> stockArrayList, Context context) {
        this.stockArrayList = stockArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public StockAnalysViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_stock_analys, parent, false);
        return new StockAnalysViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockAnalysViewHolder holder, int position) {
        Stock stock = stockArrayList.get(position);
        if (stock != null){
            holder.productQtt.setText(String.valueOf(stock.getQtt()));
            holder.productLabel.setText(stock.getLabel());
        }
        if (position % 2 == 0){
            holder.groupItem.setBackgroundResource((R.drawable.admin_rectangle_radius));
        }
        else{
            holder.groupItem.setBackgroundResource((R.drawable.admin_ractangle_blue_stock));
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public static class StockAnalysViewHolder extends RecyclerView.ViewHolder {
        private TextView productQtt;
        private TextView productLabel;
        private ConstraintLayout groupItem;
        public StockAnalysViewHolder(@NonNull View itemView) {
            super(itemView);
            productQtt = itemView.findViewById(R.id.qtt_label);
            productLabel = itemView.findViewById(R.id.product_label);
            groupItem = itemView.findViewById(R.id.item_stock);

        }
    }
}
