package com.example.android_71221.Admin.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android_71221.Adapter.ProductAdapter;
import com.example.android_71221.DB_Helper.DB_Helper;
import com.example.android_71221.Model.Product;
import com.example.android_71221.Model.ProductModel;
import com.example.android_71221.R;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class AvailableProductAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Product> productArrayList;
    private int resource;
    private OnItemListener onItemListener;
    public AvailableProductAdapter(@NonNull Context context, int resource, ArrayList<Product> productArrayList, OnItemListener onItemListener) {
        super(context, resource, productArrayList);
        this.context = context;
        this.productArrayList = productArrayList;
        this.resource = resource;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(resource, parent, false);

        ImageView productImg = view.findViewById(R.id.available_product_img);
        TextView productName = view.findViewById(R.id.available_product_name);
        TextView productPrice = view.findViewById(R.id.available_product_price);
        TextView productQtt = view.findViewById(R.id.available_product_qtt);
        ImageView deleteBtn = view.findViewById(R.id.delete_item);
        Product product = productArrayList.get(position);
        if (product != null){
            productName.setText(product.getName());
            String price = PreventNotationDouble(product.getPrice()) + " VND";
            productPrice.setText(price);
            String qtt = String.valueOf(product.getQuatity()) + " SL";
            productQtt.setText(qtt);


            String urlLink = product.getImages().get(0);
            AvailableProductAdapter.LoadImage loadImage = new AvailableProductAdapter.LoadImage(productImg);
            loadImage.execute(urlLink);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemListener.onItemClick(position);
                }
            });
        }


        return view;
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

    private String PreventNotationDouble(Double number){
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(340);
        String res = df.format(number);
        return res;
    }

    public interface OnItemListener{
        void onItemClick(int position);
    }

}
