package com.example.android_71221.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_71221.Adapter.CategoryAdapter;
import com.example.android_71221.Adapter.ProductAdapter;
import com.example.android_71221.Controller.ProductActivity;
import com.example.android_71221.DB_Helper.DB_Helper;
import com.example.android_71221.Controller.MainActivity;
import com.example.android_71221.Model.Category;
import com.example.android_71221.Model.CategoryModel;
import com.example.android_71221.Model.FavoriteModel;
import com.example.android_71221.Model.Product;
import com.example.android_71221.Model.ProductModel;
import com.example.android_71221.R;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment implements CategoryAdapter.OnCategoryAdapterListener, ProductAdapter.OnProductAdapterListener{
    private static final String DESCRIBABLE_KEY = "describable_key";
    private View view;
    private ImageView sideMenuBtn;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private RecyclerView categoryRecycleView;
    private RecyclerView productRecycleView;
    private ArrayList<Category> categoryArrayList;
    private CategoryAdapter categoryAdapter;
    private ArrayList<Product> wearableArrayList;
    private ArrayList<Product> laptopArrayList;
    private ArrayList<Product> phoneArrayList;
    private ArrayList<Product> droneArrayList;
    private ProductAdapter productAdapter;
    private GridLayoutManager categoryGridLayout;
    private LinearLayoutManager productLayoutManager;
    private CategoryModel categoryModel;
    private ProductModel productModel;
    private DB_Helper db_helper;
    private Context context;
    private ArrayList<Product> currentDataSource;
    private EditText searchBar;
    private ArrayAdapter<String> productArrayAdapter;
    private ArrayAdapter<Product> productObjectAdapter;
    private ArrayList<Product> allProductArrayList;
    private ListView searchResultListView;
    private ArrayList<Product> filteredProducts;
    private boolean isKeyboardShow;
    private FavoriteModel favoriteModel;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        reference();
        getData();

        sideMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).openNav();
            }
        });

        currentDataSource = wearableArrayList;
        productAdapter = new ProductAdapter(currentDataSource, getActivity(),this);
        productRecycleView.setAdapter(productAdapter);


        searchBar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    isKeyboardShow = true;

                }
                else{
                }
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                searchResultListView.setVisibility(View.VISIBLE);
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchResultListView.setVisibility(View.VISIBLE);

                productArrayAdapter.getFilter().filter(s);
                // filter in product to get Object cause this adapter only return string

                searchResultListView.setAdapter(productArrayAdapter);

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("textChanged", String.valueOf(s.toString()));
                String txt = s.toString();
                if (TextUtils.isEmpty(txt)){
                    searchResultListView.setVisibility(View.GONE);
                }
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideSoftKeyboard(v);
                return false;
            }
        });


        searchResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nameSelected = productArrayAdapter.getItem(position).toString();
                for (int i = 0; i < allProductArrayList.size();i++){
                    Product product = allProductArrayList.get(i);
                    if (product.getName().contains(nameSelected)){
                        Gson gson = new Gson();
                        String productJson = gson.toJson(product);
                        Intent intent = new Intent(getActivity(), ProductActivity.class);
                        intent.putExtra("productJson", productJson);
                        startActivity(intent);
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        view.clearFocus();
        isKeyboardShow = false;
        searchBar.setText("");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        db_helper = new DB_Helper(context, "EmarketDB.sqlite", null, 1);

    }


    private void reference(){
        searchBar = view.findViewById(R.id.edtHomeSearch);
        searchResultListView = view.findViewById(R.id.listView_SearchResult);
        searchResultListView.bringToFront();
        sideMenuBtn = view.findViewById(R.id.sideMenuBtn);
        categoryRecycleView = view.findViewById(R.id.recycleViewCategory);
        productRecycleView = view.findViewById(R.id.recycleProductView);
        categoryArrayList = new ArrayList<Category>();
        wearableArrayList = new ArrayList<Product>();
        laptopArrayList = new ArrayList<Product>();
        phoneArrayList = new ArrayList<Product>();
        droneArrayList = new ArrayList<Product>();

        categoryModel = new CategoryModel(db_helper);
        categoryArrayList = categoryModel.DisplayCategory();
        categoryAdapter = new CategoryAdapter(categoryArrayList,getContext(), this,0);
        productModel = new ProductModel(db_helper);
        currentDataSource = new ArrayList<Product>();

        categoryGridLayout = new GridLayoutManager(getActivity(), 4);
        categoryRecycleView.setLayoutManager(categoryGridLayout);
        categoryRecycleView.setAdapter(categoryAdapter);
        //product recyleView
        productLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        productRecycleView.setLayoutManager(productLayoutManager);

        filteredProducts = new ArrayList<Product>();
        favoriteModel = new FavoriteModel(db_helper);
        // create favorite table
        favoriteModel.CreateTableFavorite();



    }

    private void getData(){
        wearableArrayList = productModel.filterProduct(1);
        laptopArrayList = productModel.filterProduct(2);
        phoneArrayList = productModel.filterProduct(3);
        droneArrayList = productModel.filterProduct(4);
        allProductArrayList = productModel.DisplayProduct();
        ArrayList<String> productNames = new ArrayList<String>();
        for (int i = 0 ; i < allProductArrayList.size();i++){
            productNames.add(allProductArrayList.get(i).getName());
        }
        productArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, productNames);
        productObjectAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, allProductArrayList);
    }


    @Override
    public void onCategoryClick(int position) {
        Log.d("onCategoryClick", "onCategoryClick: "+ String.valueOf(position));
        switch (position){
            case 0:
                currentDataSource = wearableArrayList;
                break;
            case 1:
                currentDataSource = laptopArrayList;
                break;
            case 2:
                currentDataSource = phoneArrayList;
                break;
            case 3:
                currentDataSource = droneArrayList;
                break;
        }
        productAdapter = new ProductAdapter(currentDataSource, getActivity(),this);
        productRecycleView.setAdapter(productAdapter);
//        notifyAll();
    }

    @Override
    public void onProductClick(int position) {

        // convert to json to pass to Product Activity
        Gson gson = new Gson();
        String product = gson.toJson(currentDataSource.get(position));
        Intent intent = new Intent(getActivity(), ProductActivity.class);
        intent.putExtra("productJson", product);
        startActivity(intent);
    }

    public void hideSoftKeyboard(View view)
    {
        //Hides the SoftKeyboard
        if (isKeyboardShow){
            InputMethodManager inputMethodManager = (InputMethodManager)  getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            searchResultListView.setVisibility(View.GONE);
            view.clearFocus();
            isKeyboardShow = false;
        }

    }

}


