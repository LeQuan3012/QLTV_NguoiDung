package com.example.navigationcustom2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.navigationcustom2.Fregment.MatKhauFragment;
import com.example.navigationcustom2.Fregment.QuyDinhFragment;
import com.example.navigationcustom2.Fregment.SachFregment;
import com.example.navigationcustom2.Fregment.MuonSachFragment;
import com.example.navigationcustom2.Fregment.KeSachFregment;
import com.example.navigationcustom2.Fregment.ThonngTinCaNhanFragment;

import java.util.ArrayList;
import java.util.List;

public class MyViewAdapter extends FragmentStateAdapter {
    private final List<Fragment> fragmentList = new ArrayList<>();

    public MyViewAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment) {
        fragmentList.add(fragment);
    }
}
