package com.example.android_71221.Admin.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.android_71221.Admin.Fragment.AdminHomeFragment;
import com.example.android_71221.Admin.Fragment.PendingFragment;
import com.example.android_71221.Admin.Fragment.UserSettingsFragment;
import com.example.android_71221.Fragment.FavoriteFragment;
import com.example.android_71221.Fragment.HomeFragment;
import com.example.android_71221.Fragment.OrderFragment;
import com.example.android_71221.Fragment.ProfileFragment;

public class ViewPagerAdminAdapter extends FragmentStateAdapter {
    public ViewPagerAdminAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new AdminHomeFragment();
            case 1:
                return new PendingFragment();
            case 2:
                return new UserSettingsFragment();

            default:
                return new AdminHomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
