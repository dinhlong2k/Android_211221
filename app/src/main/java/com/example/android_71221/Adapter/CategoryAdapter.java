package com.example.android_71221.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_71221.Model.Category;
import com.example.android_71221.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private ArrayList<Category> categoryArrayList;
    private Context context;
    private  OnCategoryAdapterListener onCategoryAdapterListener;
    private int selectedItem = RecyclerView.NO_POSITION;
    public CategoryAdapter(ArrayList<Category> categoryArrayList, Context context, OnCategoryAdapterListener onCategoryAdapterListener, int selectedItem){
        this.categoryArrayList = categoryArrayList;
        this.context = context;
        this.onCategoryAdapterListener = onCategoryAdapterListener;
        this.selectedItem = selectedItem;
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view, onCategoryAdapterListener);
    }
    public void notifyItemSelected(int position){
        this.selectedItem = position;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoryArrayList.get(position);
        if (category != null){
            // set default selected
            if (position == selectedItem){
                holder.categoryLabel.setTextColor(ContextCompat.getColor(context, R.color.purple_background));
            }
            else{
                holder.categoryLabel.setTextColor(ContextCompat.getColor(context, R.color.black));
            }
            holder.categoryLabel.setText(category.getName());
            holder.categoryLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    holder.categoryLabel.setTextColor(ContextCompat.getColor(context, R.color.purple_background));
                    selectedItem = holder.getAdapterPosition();
                    onCategoryAdapterListener.onCategoryClick(holder.getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        if (categoryArrayList.size() > 0){
            return categoryArrayList.size();
        }
        return 0;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView categoryLabel;
        private OnCategoryAdapterListener onCategoryAdapterListener;
        public CategoryViewHolder(@NonNull View itemView, OnCategoryAdapterListener onCategoryAdapterListener) {
            super(itemView);
            categoryLabel = itemView.findViewById(R.id.categoryLabel);
            this.onCategoryAdapterListener = onCategoryAdapterListener;


        }

        @Override
        public void onClick(View v) {
            onCategoryAdapterListener.onCategoryClick(this.getAdapterPosition());
//            try {
//                onCategoryAdapterListener.onCategoryClick(getAdapterPosition());
//            }
//            catch (IndexOutOfBoundsException indexOutOfBoundsException){
//                return;
//            }
        }

    }
    public interface OnCategoryAdapterListener{
        void onCategoryClick(int position);
    }


}
