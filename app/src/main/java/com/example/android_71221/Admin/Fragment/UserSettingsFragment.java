package com.example.android_71221.Admin.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_71221.Admin.Adapter.UserSettingAdapter;
import com.example.android_71221.Admin.AdminActivity.AdminActivity;
import com.example.android_71221.DB_Helper.DB_Helper;
import com.example.android_71221.Model.User;
import com.example.android_71221.Model.UserModel;
import com.example.android_71221.R;

import java.util.List;

public class UserSettingsFragment extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private UserSettingAdapter userAdapter;
    private List<User> listUser;
    private DB_Helper db_helper;
    private UserModel usermodel;
    private Context context;
    private ImageView sideMenu;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
        db_helper = new DB_Helper(context, "EmarketDB.sqlite", null, 1);
        usermodel=new UserModel(db_helper);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_users_settings, container, false);

        recyclerView=view.findViewById(R.id.listviewUserSetting);
        sideMenu = view.findViewById(R.id.admin_menu_nav);

        userAdapter=new UserSettingAdapter(new UserSettingAdapter.clickDelete(){

            @Override
            public void deleteUser(User user) {
                usermodel.deleteUser(user.getId());
                loadData();
            }
        });

        listUser=usermodel.getUserWithRoleUser();

        userAdapter.setData(listUser);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(userAdapter);

        sideMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AdminActivity)getActivity()).openNav();
            }
        });
        return view;
    }

    public void loadData(){
        listUser=usermodel.getUserWithRoleUser();
        userAdapter.setData(listUser);

    }
}
