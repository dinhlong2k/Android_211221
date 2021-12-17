package com.example.android_71221.Admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_71221.DB_Helper.DB_Helper;
import com.example.android_71221.Model.Address;
import com.example.android_71221.Model.User;
import com.example.android_71221.Model.UserModel;
import com.example.android_71221.R;

import java.util.List;

public class UserSettingAdapter extends RecyclerView.Adapter<UserSettingAdapter.UserViewHolder>{

    private List<User> listUser;
    private clickDelete clickDelete;

    public UserSettingAdapter(UserSettingAdapter.clickDelete clickDelete) {
        this.clickDelete = clickDelete;
    }

    private Context context;
    public interface clickDelete{
        void deleteUser(User user);
    }

    public  void setData(List<User> list){
        this.listUser=list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_user_setting,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        User user=listUser.get(position);
        if(user==null) return;


        holder.tv_name.setText(user.getFullName());
        holder.tv_phone.setText(user.getPhoneNumber());
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickDelete.deleteUser(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listUser != null) return listUser.size();
        else return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name,tv_phone;
        ImageView btn_delete;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name=itemView.findViewById(R.id.tv_name_user_setting);
            tv_phone=itemView.findViewById(R.id.tv_phone_user_setting);
            btn_delete=itemView.findViewById(R.id.btn_delete_user_setting);

        }
    }
}

