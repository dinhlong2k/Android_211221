package com.example.android_71221.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.android_71221.Model.Product;
import com.example.android_71221.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {
    private ArrayList<String> imgURL;
    private ViewPager2 viewPager2;

    public SliderAdapter(ArrayList<String> imgURL, ViewPager2 viewPager2) {
        this.imgURL = imgURL;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slider_item_container,
                        parent,
                        false
                )
        );
    }



    @Override
    public int getItemCount() {
        Log.d("count", String.valueOf(imgURL.size()));
        return imgURL.size();
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        String urlLink = imgURL.get(position);
//        LoadImage loadImage = new LoadImage(holder.imageView);
//        loadImage.execute(urlLink);
        Picasso.get().load(urlLink).placeholder(R.drawable.ic_launcher_background).into(holder.imageView);
        if (position == imgURL.size() - 2) {
            viewPager2.post(runnable);
        }
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

    class SliderViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imageView;

        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);

        }

    }
    private  Runnable runnable = new Runnable() {
        @Override
        public void run() {
            imgURL.addAll(imgURL);
            notifyDataSetChanged();
        }
    };
}
