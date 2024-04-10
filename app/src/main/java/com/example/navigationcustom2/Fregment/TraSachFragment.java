package com.example.navigationcustom2.Fregment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.navigationcustom2.DAO.DAOMuonSach;
import com.example.navigationcustom2.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TraSachFragment extends Fragment {
    TextView txtthongtin;
    Button btnxacnhantra;
    EditText edtnhapma, edtsl;
    DAOMuonSach daoMuonSach;
    Date currentDate = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String formattedDate = dateFormat.format(currentDate);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tra_sach, container, false);
        anhXa(v);
        Bundle bundlenhan = getArguments();
        int manhan = bundlenhan.getInt("mamuon");
        int slmuon = bundlenhan.getInt("soluongmuon",0);
        String tensach = bundlenhan.getString("tensach");
        txtthongtin.setText("Bạn đang mượn "+slmuon+" quyển sách "+tensach);

        btnxacnhantra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ma = edtnhapma.getText().toString().trim();
                String sl = edtsl.getText().toString().trim();
                if (ma.isEmpty()) {
                    showDialogNotiFail("Vui lòng nhập mã để trả sách");
                }
                else if(sl.isEmpty()){
                    showDialogNotiFail("Vui lòng nhập số lượng để trả sách");
                }
                else {
                    int manhantra = Integer.parseInt(ma);
                    int sluongtra = Integer.parseInt(sl);
                    if(sluongtra > slmuon){
                        showDialogNotiFail("Số lượng trả không hợp lệ");
                    }
                    else{
                        if (manhantra == manhan) {
                            if(daoMuonSach.update_trasach("3",bundlenhan.getString("tensach"), bundlenhan.getString("ngaymuon"),sluongtra,formattedDate)>0){
                                showDialogNotiSuccess("Trả sách thành công");
                                HomeFragment fragment = new HomeFragment();
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.content_frame, fragment)
                                        .addToBackStack(null)
                                        .commit();
                            }
                            else {
                                showDialogNotiSuccess("Trả sách thất bại. Vui lòng kiểm tra lại thông tin");
                            }
                        }
                        else{
                            showDialogNotiFail("Bạn nhập sai mã trả sách");
                        }
                    }
                }
            }
        });
        return v;
    }

    public  void showDialogNotiFail(String notit){
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.quan_thongbao_thatbai);
        Window win = dialog.getWindow();
        if(win == null){
            return;
        }
        win.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        win.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams window = win.getAttributes();
        window.gravity = Gravity.CENTER;
        win.setAttributes(window);
        TextView txt = dialog.findViewById(R.id.txtFail);
        txt.setText(notit);
        Button btnthoat = dialog.findViewById(R.id.btnCancelFail);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Đóng dialog sau 2 giây
                dialog.dismiss();
            }
        }, 2000);
        dialog.show();
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    public  void showDialogNotiSuccess(String notit){
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.quan_layout_thongbao_ok);
        Window win = dialog.getWindow();
        if(win == null){
            return;
        }
        win.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        win.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams window = win.getAttributes();
        window.gravity = Gravity.CENTER;
        win.setAttributes(window);
        TextView txt = dialog.findViewById(R.id.txtNotification);
        txt.setText(notit);
        Button btnthoat = dialog.findViewById(R.id.btnCancelNotit);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Đóng dialog sau 2 giây
                dialog.dismiss();
            }
        }, 2000);
        dialog.show();
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void anhXa(View v) {
        btnxacnhantra = v.findViewById(R.id.btnxacnhantrasach);
        edtnhapma = v.findViewById(R.id.edtnhapmatrasach);
        daoMuonSach = new DAOMuonSach(v.getContext());
        txtthongtin = v.findViewById(R.id.txtthongtinmuon);
        edtsl = v.findViewById(R.id.edtnhapsltrasach);
    }
}