package com.example.android_71221.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android_71221.Controller.ProductActivity;
import com.example.android_71221.Fragment.OrderFragment;
import com.example.android_71221.Model.Basket;
import com.example.android_71221.Model.Product;
import com.example.android_71221.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class BasketAdapter extends ArrayAdapter {
    private Context context;
    private int resource;
    private ArrayList<Product> productArrayList;
    EventListener listener;

    public BasketAdapter(@NonNull Context context, int resource, ArrayList<Product> productArrayList, EventListener listener) {
        super(context, resource, productArrayList);
        this.context = context;
        this.resource = resource;
        this.productArrayList = productArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(resource, parent, false);

        ImageView imageView = view.findViewById(R.id.imageproduct);
        TextView productName = view.findViewById(R.id.nameproduct);
        TextView productQualtity =view.findViewById(R.id.qualtity);
        TextView productPrice =view.findViewById(R.id.price);
        ImageButton increaseBtn = view.findViewById(R.id.increaseBtn);
        ImageButton decreaseBtn = view.findViewById(R.id.decreaseBtn);
        ImageView deleteBtn = view.findViewById(R.id.delete_item);
        Product product = Basket.basket.get(position);
        String urlLink = product.getImages().get(0);
//        LoadImage loadImage = new LoadImage(imageView);
//        loadImage.execute(urlLink);
        Picasso.get().load(urlLink).placeholder(R.drawable.ic_launcher_background).into(imageView);


        productName.setText(product.getName());
        String price = PreventNotationDouble(product.getPrice()) + " VND";
        productPrice.setText(price);
        productQualtity.setText(String.valueOf(product.getQuatity()));

        final int[] qtt = {Integer.parseInt(productQualtity.getText().toString().trim())};
        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // display counter
                qtt[0] +=1;
                Log.d("qtt", "getView: "+ String.valueOf(qtt[0]));
                productQualtity.setText(String.valueOf(qtt[0]));

                // update quality in glocal basket
                product.setQuatity(qtt[0]);
                listener.onEvent();

                //update totalprice

            }
        });
        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qtt[0] -= 1;
                if (qtt[0] >= 1) {
                    // display counter
                    Log.d("qtt", "getView: "+ String.valueOf(qtt[0]));
                    productQualtity.setText(String.valueOf(qtt[0]));

                    // update quality in glocal basket
                    product.setQuatity(qtt[0]);
                    // delegate to order fragment
                    listener.onEvent();

                }
                else{
                    qtt[0] = 1;
                }
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDelete(product);

            }
        });



        return view;
    }

    private void DialogDelete(Product product){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Bạn có đồng ý xóa sản phẩm " + product.getName() + " khỏi giỏ hàng không ?");
        // yes answer
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int id = product.getId();
                for (int i = 0 ; i < Basket.basket.size(); i++){
                    Product product1 = Basket.basket.get(i);
                    if (id == product1.getId()){
                        Basket.basket.remove(i);
                        break;
                    }
                }
                notifyDataSetChanged();
                listener.onEvent();
            }
        });

        //no answer
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();

    }
    private String PreventNotationDouble(Double number){
        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        df.setMaximumFractionDigits(340);
        String res = df.format(number);
        return res;
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

    public interface EventListener {
        void onEvent();
    }
}
