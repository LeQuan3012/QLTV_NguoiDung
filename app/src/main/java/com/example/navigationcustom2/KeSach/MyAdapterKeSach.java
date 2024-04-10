package com.example.navigationcustom2.KeSach;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationcustom2.DAO.DAOKeSach;
import com.example.navigationcustom2.Fregment.HomeFragment;
import com.example.navigationcustom2.Fregment.KeSachFregment;
import com.example.navigationcustom2.Fregment.SachFregment;
import com.example.navigationcustom2.R;

import java.util.ArrayList;

public class MyAdapterKeSach extends RecyclerView.Adapter<MyAdapterKeSach.MyViewHolderKeSach> {
    Context mcontext;
    ArrayList<MyKeSach> listkesach;
    DAOKeSach daoKeSach;

    public MyAdapterKeSach(Context mcontext) {
        this.mcontext = mcontext;
        daoKeSach = new DAOKeSach(mcontext);
    }
    public void setData(ArrayList<MyKeSach> list){
        this.listkesach = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolderKeSach onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemloaisach,parent, false);
        return new MyViewHolderKeSach(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderKeSach holder, int position) {
        MyKeSach myKeSach = listkesach.get(position);
        holder.txtten.setText(myKeSach.getTenTheLoai());
        holder.txtsl.setText("SL sách: "+daoKeSach.getSLSach(myKeSach.getMaTheLoai()));
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = ((AppCompatActivity)mcontext).getSupportFragmentManager();
                SachFregment fragment = new SachFregment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putInt("maloaisach", myKeSach.getMaTheLoai());
                bundle.putString("tentheloai",myKeSach.getTenTheLoai());
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return listkesach.size();
    }

    public class MyViewHolderKeSach extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView txtten, txtsl;
        public MyViewHolderKeSach(@NonNull View itemView) {
            super(itemView);
            txtsl = itemView.findViewById(R.id.soLuongSach);
            txtten = itemView.findViewById(R.id.nameLoaiSach);
            cardView = itemView.findViewById(R.id.myCartView);
        }
    }
}
