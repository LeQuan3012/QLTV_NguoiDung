package com.example.navigationcustom2.Fregment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationcustom2.DAO.DAOKeSach;
import com.example.navigationcustom2.KeSach.MyAdapterKeSach;
import com.example.navigationcustom2.KeSach.MyKeSach;
import com.example.navigationcustom2.R;

import java.util.ArrayList;

public class KeSachFregment extends Fragment {
    TextView txttenke, txtsl;
    RecyclerView recy;
    DAOKeSach daoKeSach;
    ArrayList<MyKeSach> listkesach;
    MyAdapterKeSach myadapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_kesach, container, false);
        anhXa(v);
        listkesach = new ArrayList<>();
        daoKeSach = new DAOKeSach(v.getContext());
        myadapter = new MyAdapterKeSach(v.getContext());
        myadapter.setData(getData());
        recy.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recy.setAdapter(myadapter);
        return v;
    }

    private ArrayList<MyKeSach> getData() {
        return daoKeSach.getAllLoaiSach();
    }

    @SuppressLint("WrongViewCast")
    private void anhXa(View v) {
        txttenke = v.findViewById(R.id.nameLoaiSach);
        txtsl = v.findViewById(R.id.soLuongSach);
        recy = v.findViewById(R.id.recycleview);
    }
}

