package com.example.android_71221.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android_71221.Model.Basket;
import com.example.android_71221.Model.Product;
import com.example.android_71221.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class FavoriteAdapter extends ArrayAdapter {
    private Context context;
    private int resource;
    private ArrayList<Product> productArrayList;
    EventListener listener;
    public FavoriteAdapter(@NonNull Context context, int resource, ArrayList<Product> productArrayList, EventListener listener) {
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
        Product product = productArrayList.get(position);
        ImageView deleteBtn = view.findViewById(R.id.delete_item);
        TextView productName = view.findViewById(R.id.available_product_name);
        TextView productPrice = view.findViewById(R.id.available_product_price);
        ImageView img = view.findViewById(R.id.available_product_img);
        String price = PreventNotationDouble(product.getPrice()) + " VND";
        productName.setText(product.getName());
        productPrice.setText(price);

        String urlLink = product.getImages().get(0);
        Picasso.get().load(urlLink).placeholder(R.drawable.ic_launcher_background).into(img);

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
        builder.setMessage("B???n c?? ?????ng ?? x??a s???n ph???m " + product.getName() + " kh???i danh s??ch y??u th??ch ?");
        // yes answer
        builder.setPositiveButton("C??", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onEvent(product);
                notifyDataSetChanged();
            }
        });

        //no answer
        builder.setNegativeButton("Kh??ng", new DialogInterface.OnClickListener() {
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

    public interface EventListener {
        void onEvent(Product product);
    }
}
