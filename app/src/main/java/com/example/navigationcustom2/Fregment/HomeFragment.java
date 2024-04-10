package com.example.navigationcustom2.Fregment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.navigationcustom2.Fregment.KeSachFregment;
import com.example.navigationcustom2.Fregment.MuonSachFragment;
import com.example.navigationcustom2.Fregment.SachFregment;
import com.example.navigationcustom2.MyViewAdapter;
import com.example.navigationcustom2.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class HomeFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        tabLayout = rootView.findViewById(R.id.tab_layout);
        viewPager2 = rootView.findViewById(R.id.view_pager2);

        // Gọi hàm setupViewPager() để thiết lập TabLayout và ViewPager
        String[] tabTitles = {"Kệ sách", "Mượn sách"};
        setupViewPager(viewPager2);
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> tab.setText(tabTitles[position])
        ).attach();


        return rootView;
    }

    private void setupViewPager(ViewPager2 viewPager) {
        MyViewAdapter adapter = new MyViewAdapter(requireActivity()); // Sử dụng requireActivity() để đảm bảo rằng activity hiện tại không null

        // Thêm các fragment vào adapter
        adapter.addFragment(new KeSachFregment());
        adapter.addFragment(new MuonSachFragment());

        // Thiết lập adapter cho ViewPager
        viewPager.setAdapter(adapter);

    }

}