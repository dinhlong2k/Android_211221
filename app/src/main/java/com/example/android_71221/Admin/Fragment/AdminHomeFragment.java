package com.example.android_71221.Admin.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_71221.Admin.Adapter.AvailableProductAdapter;
import com.example.android_71221.Admin.Adapter.StockAnalysAdapter;
import com.example.android_71221.Admin.AdminActivity.AddProductActivity;
import com.example.android_71221.Admin.AdminActivity.AdminActivity;
import com.example.android_71221.Admin.Model.Upload;
import com.example.android_71221.DB_Helper.DB_Helper;
import com.example.android_71221.Model.Product;
import com.example.android_71221.Model.ProductModel;
import com.example.android_71221.Model.Stock;
import com.example.android_71221.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class AdminHomeFragment extends Fragment implements AvailableProductAdapter.OnItemListener {
    private View view;
    private ImageView sideMenu;
    private RecyclerView stockAnalysRV;
    private StockAnalysAdapter stockAnalysAdapter;
    private GridLayoutManager stockGrid;
    int spacingInPixels = 8;
    private AvailableProductAdapter availableProductAdapter;
    private ArrayList<Product> productArrayList = new ArrayList<Product>();
    private ListView availableListView;
    private Button addProductBtn;
    private DB_Helper db_helper;
    private ProductModel productModel;
    private Context context;
    private ArrayList<Stock> stockArrayList = new ArrayList<Stock>();

    private DatabaseReference databaseReference;
    private List<Upload> uploadList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_home, container, false);
        reference();
        PopulateData();

        sideMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AdminActivity)getActivity()).openNav();
            }
        });

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddProductActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        db_helper = new DB_Helper(context, "EmarketDB.sqlite", null, 1);

    }

    public void PopulateData(){
        productArrayList = productModel.DisplayProduct();
        availableProductAdapter = new AvailableProductAdapter(context, R.layout.item_available_product, productArrayList, this);
        availableListView.setAdapter(availableProductAdapter);

        int productIn = productModel.ProductIn();
        int productOut = productModel.ProductOut();
        stockArrayList.clear();
        stockArrayList.add(new Stock(productIn, "Có Sẵn"));
        stockArrayList.add(new Stock(productOut, "Đã Bán"));
        stockAnalysAdapter = new StockAnalysAdapter(stockArrayList, context);
        stockAnalysRV.setAdapter(stockAnalysAdapter);
//        stockAnalysRV.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
    }

    @Override
    public void onResume() {
        super.onResume();
        PopulateData();

    }

    private void reference(){
        productModel = new ProductModel(db_helper);
        sideMenu = view.findViewById(R.id.admin_menu_nav);
        stockAnalysRV = view.findViewById(R.id.productAnalysRecycleView);

        // test static data
        stockArrayList = new ArrayList<Stock>();

        stockGrid = new GridLayoutManager(getActivity(), 2);
        stockAnalysRV.setLayoutManager(stockGrid);

        availableListView = view.findViewById(R.id.availableListView);

        addProductBtn = view.findViewById(R.id.addProductBtn);

    }

    @Override
    public void onItemClick(int position) {
        Log.d("onItemClick", "onItemClick: "+ String.valueOf(position));
        productModel.DeleteProduct(productArrayList.get(position));
        PopulateData();
    }
}

class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = space;
        } else {
            outRect.top = 0;
        }
    }
}
