package com.example.navigationcustom2.Fregment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.navigationcustom2.DAO.DAOMuonSach;
import com.example.navigationcustom2.DAO.DAOSach;
import com.example.navigationcustom2.MuonSach.MyMuonSach;
import com.example.navigationcustom2.R;

import java.text.SimpleDateFormat;
import java.util.Date;


public class XemChiTietSachFragment extends Fragment {
    ImageView img;
    ImageButton btnquaylai, btnxoa,btnsua;
    Button btnmuon;
    DAOMuonSach daoMuonSach;
    DAOSach daoSach;
    TextView txttensach, txttacgia, txttheloai, txttomtat, txtngayxb, txtnhaxb, txtslkho, txtslsachdangmuon, txtsosachcon;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_xem_chi_tiet_sach, container, false);
        anhxa(v);
        Bundle nhan = getArguments();

        byte[] hinhanh = nhan.getByteArray("anh");
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh, 0, hinhanh.length);
        img.setImageBitmap(bitmap);

        txttensach.setText(nhan.getString("tensach"));
        int masach = daoSach.getMaSach(txttensach.getText().toString());

        int matacgia = nhan.getInt("tg");
        String tentacgia = daoSach.get_tacgia(matacgia);
        txttacgia.setText(tentacgia);

        int theloai = nhan.getInt("theloai");
        String tentheloai = daoSach.gettenthloai(theloai);
        txttheloai.setText(tentheloai);

        txttomtat.setText(nhan.getString("tomtat"));
        txtngayxb.setText(nhan.getString("ngayxuatban"));
        txtslsachdangmuon.setText(daoMuonSach.getSoSachdangmuon(daoSach.getMaSach(txttensach.getText().toString()))+"");

        int sosachconlai = nhan.getInt("soluong") - daoMuonSach.getSoSachdangmuon(daoSach.getMaSach(txttensach.getText().toString()));
        txtsosachcon.setText(sosachconlai+"");

        int nhaxb = nhan.getInt("nxb");
        String tennhaxb = daoSach.gettennhaxb(nhaxb);
        txtnhaxb.setText(tennhaxb);

        txtslkho.setText(nhan.getInt("soluong")+"");

        btnmuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sosachconlai > 0) {
                    OpenFeedbackMuon(txttensach.getText().toString(),tentacgia,sosachconlai,nhan.getInt("soluong"));
                }
                else{
                    showDialogNotiFail("Số lượng sách còn lại trong kho không đủ. Mong bạn thông cảm");
                }
            }
        });


        return v;
    }


    private void OpenFeedbackMuon(String ts, String tg, int sosachconlai, int soluong){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.quan_layout_dialog_muon_sach);
        Window win = dialog.getWindow();
        if(win == null){
            return;
        }
        win.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        win.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowattribute = win.getAttributes();
        windowattribute.gravity = Gravity.CENTER;
        win.setAttributes(windowattribute);
        Button btnmuon = dialog.findViewById(R.id.btnmuonsach);
        Button btnhuy = dialog.findViewById(R.id.btnhuymuon);
        TextView txttens = dialog.findViewById(R.id.edttensachmuon);
        txttens.setText(ts);
        TextView txttacgias = dialog.findViewById(R.id.edttacgiamuon);
        txttacgias.setText(tg);

        EditText sl = dialog.findViewById(R.id.edtsoluongmuon);
        sl.setText("1");

        TextView txtngaymuon = dialog.findViewById(R.id.edtngaymuon);
        Date datecurrent = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = dateFormat.format(datecurrent);
        txtngaymuon.setText(format);
        dialog.show();
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnmuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soluongt = Integer.parseInt(sl.getText().toString());
                if(soluongt<=0){
                    showDialogNotiFail("Số lượng mượn không được nhỏ hơn 1");
                }
                else if(soluongt > sosachconlai){
                    showDialogNotiFail("Số lượng không đủ cho bạn mượn");
                }
                else{
                    int masach = Integer.parseInt(daoMuonSach.getmasach(txttens.getText().toString()));
                    String ngaymuon= txtngaymuon.getText().toString();
                    MyMuonSach obj = new MyMuonSach(
                            3,
                            masach,
                            ngaymuon,
                            "",
                            "Chưa trả",
                            0,
                            soluongt
                    );
                    if(daoMuonSach.insert_muonsach(obj) > 0){
                        showDialogNotiSuccess("Mượn sách thành công");
                        dialog.dismiss();
                        txtslsachdangmuon.setText(daoMuonSach.getSoSachdangmuon(daoSach.getMaSach(txttensach.getText().toString()))+"");
                        int sosachconlai = soluong - daoMuonSach.getSoSachdangmuon(daoSach.getMaSach(txttensach.getText().toString()));
                        txtsosachcon.setText(sosachconlai+"");
                    }
                    else {
                        showDialogNotiFail("Mượn sách thất bại. Vui lòng kiểm tra lại");
                    }
                }
            }
        });
    }
    public void showDialogNotiSuccess(String notit){
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
    public void showDialogNotiFail(String notit){
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

    private void anhxa(View v) {
        img = v.findViewById(R.id.imgxemchitiet);
        txttensach = v.findViewById(R.id.txtchitiettensach);
        txttacgia = v.findViewById(R.id.txtchitiettacgia);
        txttheloai = v.findViewById(R.id.txtchitiettheloai);
        txttomtat = v.findViewById(R.id.txtchitiettomtat);
        txtngayxb = v.findViewById(R.id.txtchitietngayxb);
        txtnhaxb = v.findViewById(R.id.txtchitietnhaxb);
        txtslkho = v.findViewById(R.id.txtchitietslkho);
        txtslsachdangmuon = v.findViewById(R.id.txtchitietslmuon);
        txtsosachcon = v.findViewById(R.id.txtchitietsoluoncon);
        daoSach = new DAOSach(v.getContext());
        btnmuon = v.findViewById(R.id.btnmuonsach);
        daoMuonSach = new DAOMuonSach(v.getContext());
    }
}