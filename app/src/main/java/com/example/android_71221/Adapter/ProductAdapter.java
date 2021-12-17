package com.example.android_71221.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_71221.Model.Product;
import com.example.android_71221.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private ArrayList<Product> mProducts;
    Context context;
    private OnProductAdapterListener onProductAdapterListener;
    public ProductAdapter(ArrayList<Product> mProducts, Context context, OnProductAdapterListener onProductAdapterListener) {
        this.mProducts = mProducts;
        this.context = context;
        this.onProductAdapterListener = onProductAdapterListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, ProductActivity.class);
//                context.startActivity(intent);
//            }
//        });
        return new ProductViewHolder(view, onProductAdapterListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = mProducts.get(position);
//        holder.itemImage.setImageResource(product.getImages().indexOf(0));
        String urlLink = product.getImages().get(0);
//        LoadImage loadImage = new LoadImage(holder.itemImage);
//        loadImage.execute(urlLink);
        Picasso.get().load(urlLink).placeholder(R.drawable.ic_launcher_background).into(holder.itemImage);
        holder.itemName.setText(product.getName());
        holder.itemVersion.setText(product.getVersion());
        String price = PreventNotationDouble(product.getPrice());
        holder.itemPrice.setText(price);

    }

    @Override
    public int getItemCount() {
        if (mProducts.size() != 0){
            return mProducts.size();
        }
        return 0;
    }

    private String PreventNotationDouble(Double number){
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(340);
        String res = df.format(number);
        return res;
    }


    public static class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView itemImage;
        private TextView itemName;
        private TextView itemVersion;
        private TextView itemPrice;
        private OnProductAdapterListener onProductAdapterListener;
        public ProductViewHolder(@NonNull View itemView, OnProductAdapterListener onProductAdapterListener) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemName = itemView.findViewById(R.id.item_name);
            itemVersion = itemView.findViewById(R.id.item_version);;
            itemPrice = itemView.findViewById(R.id.item_price);
            itemView.setOnClickListener(this);

            this.onProductAdapterListener = onProductAdapterListener;
        }

        @Override
        public void onClick(View v){
            onProductAdapterListener.onProductClick(getAdapterPosition());
        }


    }
    public interface OnProductAdapterListener{
        void onProductClick(int position);
    }

    private class LoadImage extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;
        public LoadImage(ImageView imageView){
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlLink = strings[0];
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new java.net.URL(urlLink).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }
}
