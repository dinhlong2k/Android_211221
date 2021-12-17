package com.example.android_71221.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.example.android_71221.Adapter.BasketAdapter;
import com.example.android_71221.Adapter.FavoriteAdapter;
import com.example.android_71221.Controller.MainActivity;
import com.example.android_71221.DB_Helper.DB_Helper;
import com.example.android_71221.Model.FavoriteModel;
import com.example.android_71221.Model.Product;
import com.example.android_71221.Model.ProductModel;
import com.example.android_71221.R;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment implements FavoriteAdapter.EventListener {

    private View view;
    private ImageView sideMenuBtn;
    private ListView listViewFav;
    private FavoriteAdapter favoriteAdapter;
    private Context context;
    private FavoriteModel favoriteModel;
    private ProductModel productModel;
    private DB_Helper db_helper;
    private final static String USER_INFO= "USER_INFO";
    private int idUser = 0;
    private ArrayList<Product> productArrayList;
    private ArrayList<Integer> productIDs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorite, container, false);

        reference();
        SharedPreferences mPrefs =  this.getActivity().getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        idUser = mPrefs.getInt("id_user",0);
        clickEventListener();
        return view;
    }

    private void reference() {
        sideMenuBtn = view.findViewById(R.id.sideMenuBtn);
        listViewFav = view.findViewById(R.id.listViewFav);
        favoriteModel = new FavoriteModel(db_helper);
        productModel= new ProductModel(db_helper);
        productArrayList = new ArrayList<Product>();
        productIDs = new ArrayList<Integer>();

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("onResume", "onResume: ");
        PopulateData();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        db_helper = new DB_Helper(context, "EmarketDB.sqlite", null, 1);
    }

    public void PopulateData(){
        productIDs.clear();
        productArrayList.clear();
        productIDs = favoriteModel.SelectFavProduct(idUser);
        for (int i = 0 ; i< productIDs.size() ;i++){
            int idProduct = productIDs.get(i);
            Product product = productModel.GetProductById(idProduct);
            productArrayList.add(product);
        }
        favoriteAdapter = new FavoriteAdapter(context, R.layout.item_favorite, productArrayList, this);
        listViewFav.setAdapter(favoriteAdapter);
    }

    private void clickEventListener(){
        sideMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).openNav();
            }
        });
    }

    @Override
    public void onEvent(Product product) {
        favoriteModel.DeleteFavoriteData(idUser, product.getId());
        PopulateData();
    }
}
