package com.example.navigationcustom2.DAO;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.navigationcustom2.Sach.MySach;
import com.example.navigationcustom2.databases.DB;

import java.util.ArrayList;
import java.util.List;

public class DAOSach {
    private SQLiteDatabase database;
    public DAOSach(Context ct){
        DB db = new DB(ct);
        database = db.getWritableDatabase();
    }
    public int getMaSach(String ten){
        int ma=0;
        String sql = "select MaSach from Sach where TenSach=?";
        Cursor c = database.rawQuery(sql, new String[]{ten});
        if(c != null && c.moveToFirst()){
            ma = c.getInt(0);
            c.close();
        }
        return ma;
    }
    public String getTenSach (int ma){
        String name = "";
        String sql = "select TenSach from Sach where MaSach=?";
        Cursor c = database.rawQuery(sql, new String[]{String.valueOf(ma)});
        if(c!= null && c.moveToFirst()){
            name = c.getString(0);
        }
        return name;
    }
    public String gettennhaxb(int ma){
        String ten="";
        String sql = "select TenNXB from NhaXuatBan where MaNXB=?";
        Cursor c = database.rawQuery(sql, new String[]{String.valueOf(ma)});
        if (c != null && c.moveToFirst()) {
            ten = c.getString(0);
        }
        return ten;
    }
    public String gettenthloai(int ma){
        String ten="";
        String sql = "select TenTheLoai from TheLoaiSach where MaTheLoai=?";
        Cursor c = database.rawQuery(sql, new String[]{String.valueOf(ma)});
        if (c != null && c.moveToFirst()) {
            ten = c.getString(0);
        }
        return ten;
    }
    public List<MySach> getAll(int ma){
        String sql = "select * from Sach where MaTheLoai=?";
        Cursor c = database.rawQuery(sql, new String[]{ma+""});
        List<MySach> list = new ArrayList<>();
        while (c.moveToNext()){
            MySach obj = new MySach();
            obj.setMaSach(c.getInt(0));
            obj.setTenSach(c.getString(1));
            obj.setMaTheLoai(c.getInt(2));
            obj.setTomTat(c.getString(3));
            obj.setMatacGia(c.getInt(4));
            obj.setNgayXuatBan(c.getString(5));
            obj.setManxb(c.getInt(6));
            obj.setSoLuong(c.getInt(7));
            byte[] hinhAnh = c.getBlob(8);
            if (hinhAnh != null) {
                obj.setHinhAnh(hinhAnh);
            }
            list.add(obj);
        }
        c.close();
        return list;
    }
    @SuppressLint("Range")
    public String get_tacgia(int ma){
        String tenTacGia = "";
        String sql = "select TenTacGia from TacGia where MaTacGia=?";
        Cursor cursor = database.rawQuery(sql, new String[]{String.valueOf(ma)});
        if (cursor != null && cursor.moveToFirst()) {
            tenTacGia = cursor.getString(0);
            cursor.close();
        }
        return tenTacGia;
    }
}
