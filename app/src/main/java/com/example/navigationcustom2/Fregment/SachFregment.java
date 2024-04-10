package com.example.navigationcustom2.Fregment;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationcustom2.DAO.DAOSach;
import com.example.navigationcustom2.R;
import com.example.navigationcustom2.Sach.MyAdapterSach;
import com.example.navigationcustom2.Sach.MySach;

import java.util.ArrayList;
import java.util.List;

public class SachFregment extends Fragment {

    RecyclerView recy;
    DAOSach daoSach ;
    MyAdapterSach adapterSach;
    EditText txttimkiem;
    TextView txtsoluong, txttentheloaisach;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sach, container, false);
        anhXa(v);

        Bundle nhan = getArguments();
        int maloaisach = nhan.getInt("maloaisach",0);
        String tentheloai = nhan.getString("tentheloai");
        txttentheloaisach.setText(tentheloai);
        adapterSach = new MyAdapterSach(v.getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recy.setLayoutManager(linearLayoutManager);
        recy.setAdapter(adapterSach);
        adapterSach.setData(getData(maloaisach));
        int sl = adapterSach.getItemCount();
        txtsoluong.setText(sl+"");
        txttimkiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterRecycleView(charSequence.toString().trim(), maloaisach);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return v;
    }

    private void filterRecycleView(String searchText, int ma){
        List<MySach> fillSach = new ArrayList<>();
        for(MySach sach: getData(ma)){
            if(sach.getTenSach().toLowerCase().contains(searchText.toLowerCase())){
                fillSach.add(sach);
            }
        }
        adapterSach.setData(fillSach);
    }
    private List<MySach> getData(int ma) {
        return daoSach.getAll(ma);
    }

    private void anhXa(View k) {
        recy = k.findViewById(R.id.recycleviewsach);
        txttimkiem = k.findViewById(R.id.edttimkiem);
        txtsoluong = k.findViewById(R.id.txtsoluongsach);
        daoSach = new DAOSach(k.getContext());
        txttentheloaisach = k.findViewById(R.id.txttheloai);
    }
}
