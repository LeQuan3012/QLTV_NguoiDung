package com.example.navigationcustom2.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.navigationcustom2.MuonSach.MyMuonSach;
import com.example.navigationcustom2.databases.DB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DAOMuonSach {
    private SQLiteDatabase database;
    private DAOSach daoSach;
    public DAOMuonSach(Context ct){
        DB db = new DB(ct);
        daoSach = new DAOSach(ct);
        database = db.getWritableDatabase();
    }
    public int getSoSachdangmuon(int masach){
        int num=0;
        String sql = "select sum(SoLuong) from MuonSach where MaSach=? and TinhTrang=? group by MaSach";
        Cursor c = database.rawQuery(sql,new String[]{String.valueOf(masach),"Chưa trả"});
        if (c != null && c.moveToFirst()) {

            num = Integer.parseInt(c.getString(0));
            c.close();
        }
        return num;
    }
    public int getTinhTrangMuon(int mamuonsach){
        int kq=0;
        String sql = "select TinhTrang from MuonSach where MaMuonSach=? and TinhTrang=?";
        Cursor c = database.rawQuery(sql, new String[]{mamuonsach+"","Chưa trả"});
        if(c!=null && c.moveToFirst()){
            kq=1;
        }
        return kq;
    }
    public List<MyMuonSach> getAllSachMuon(int maquanly){
        List<MyMuonSach> list = new ArrayList<>();
        String sql = "select * from MuonSach where MaQuanLy=?";
        Cursor c = database.rawQuery(sql, new String[]{String.valueOf(maquanly)});
        if(c == null){
            return list = null;
        }
        while (c.moveToNext()){
            MyMuonSach obj = new MyMuonSach();
            obj.setMamuonsach(Integer.parseInt(c.getString(0)));
            obj.setMaquanly(Integer.parseInt(c.getString(1)));
            obj.setMasach(c.getInt(2));
            obj.setNgaymuon(c.getString(3));
            obj.setNgaytra(c.getString(4));
            obj.setTinhtrang(c.getString(5));
            obj.setPhuphi(c.getInt(6));
            list.add(obj);
        }
        return list;
    }
    public int getsosachdamuon(int maquanly){
        int kq = 0;
        String sql = "select sum(SoLuong) from MuonSach where MaQuanLy=? and TinhTrang=? group by MaQuanLy";
        Cursor c = database.rawQuery(sql, new String[]{maquanly+"","Đã trả"});
        if(c!= null && c.moveToFirst()){
            kq = c.getInt(0);
        }
        return kq;
    }
    public int getsosachdangmuon(int maquanly){
        int kq = 0;
        String sql = "select sum(SoLuong) from MuonSach where MaQuanLy=? and TinhTrang=? group by MaQuanLy";
        Cursor c = database.rawQuery(sql, new String[]{maquanly+"","Chưa trả"});
        if(c!= null && c.moveToFirst()){
            kq = c.getInt(0);
        }
        return kq;
    }

    public int getSLMuonTheoSach(int mamuon, int masach, int maquanly){
        int sl = 0;
        String sql ="select SoLuong from MuonSach where MaMuonSach=? and MaSach=? and MaQuanLy=?";
        Cursor c = database.rawQuery(sql, new String[]{mamuon+"",masach+"",maquanly+""});
        if(c!=null && c.moveToFirst()){
            sl = c.getInt(0);
        }
        return sl;
    }
    public String getNgayTraMuonSachTheoSach(int masach, int maquanly){
        String t = "";
        String sql ="select NgayTra from MuonSach where MaSach=? and MaQuanLy=? and TinhTrang=?";
        Cursor c = database.rawQuery(sql, new String[]{masach+"",maquanly+"","Đã trả"});
        if(c!=null && c.moveToFirst()){
            t = c.getString(0);
        }
        return t;
    }
    public int update_trasach(String maquanly,String tensach, String ngaymuon, int soluong, String ngaytra){
        long phuphi=0;
        Date ngaymuonsach = stringtoDate(ngaymuon);
        Date ngaytrasach = stringtoDate(ngaytra);

        long somiligiay = ngaytrasach.getTime() - ngaymuonsach.getTime();
        long songay = 0;
        if(somiligiay >= 1){
            songay = somiligiay/(1000*6000*60*24);
        }
        if(songay<=60){
            phuphi = 0;
        }
        else{
            long quahan = songay - 30;
            phuphi = quahan*10000;
        }
        if(getSLmuontheosachDeTra(maquanly,tensach, ngaymuon)==soluong){
            String mamuonsach = getMaMuonSach(maquanly,tensach, ngaymuon);
            ContentValues values = new ContentValues();
            values.put("NgayTra", ngaytra);
            values.put("TinhTrang", "Đã trả");
            values.put("PhuPhi", phuphi);
            return database.update("MuonSach",values,"MaMuonSach=?", new String[]{mamuonsach});
        }
        else{
            String mamuonsach = getMaMuonSach(maquanly,tensach, ngaymuon);
            ContentValues values = new ContentValues();
            values.put("SoLuong",getSLmuontheosachDeTra(maquanly,tensach, ngaymuon)-soluong);
            database.update("MuonSach",values,"MaMuonSach=?", new String[]{mamuonsach});
            ContentValues valuesthem = new ContentValues();
            valuesthem.put("MaQuanLy",maquanly);
            valuesthem.put("MaSach",daoSach.getMaSach(tensach));
            valuesthem.put("NgayMuon",ngaymuon);
            valuesthem.put("NgayTra",ngaytra);
            valuesthem.put("TinhTrang","Đã trả");
            valuesthem.put("PhuPhi",phuphi);
            valuesthem.put("SoLuong",soluong);
            database.insert("MuonSach",null,valuesthem);
            return 1;
        }
    }
    public int getSLmuontheosachDeTra(String maquanly, String tensach, String ngaymuon){
        int sl=0;
        String masach = getmasach(tensach);
        String sql = "select SoLuong from MuonSach where MaQuanLy=? and MaSach=? and NgayMuon=? and TinhTrang=?";
        Cursor c = database.rawQuery(sql, new String[]{maquanly,masach,ngaymuon,"Chưa trả"});
        if (c != null && c.moveToFirst()) {
            sl = c.getInt(0);
            c.close();
        }
        return sl;

    }
    public int getSoSachMuonTheoSach(int mamuonsach){
        int sl =0;
        String sql = "select SoLuong from MuonSach where MaMuonSach=? group by MaMuonSach";
        Cursor c = database.rawQuery(sql, new String[]{String.valueOf(mamuonsach)});
        if(c!= null && c.moveToFirst()){
            sl = c.getInt(0);
        }
        return sl;
    }

    public int getsosachquahan(int maquanly){
        int kq = 0;
        String sql = "SELECT SUM(SoLuong) FROM MuonSach WHERE MaQuanLy=? AND (ROUND(julianday('now') - julianday(NgayMuon)) > 60) AND TinhTrang='Chưa trả' GROUP BY MaQuanLy";
        Cursor c = database.rawQuery(sql, new String[]{maquanly+""});
        if(c!= null && c.moveToFirst()){
            kq = c.getInt(0);
        }
        return kq;
    }
    public String getMaMuonSach(String maquanly, String tensach, String ngaymuon){
        String ma="";
        String masach = getmasach(tensach);
        String sql = "select MaMuonSach from MuonSach where MaQuanLy=? and MaSach=? and NgayMuon=? and TinhTrang=?";
        Cursor c = database.rawQuery(sql, new String[]{maquanly,masach,ngaymuon,"Chưa trả"});
        if (c != null && c.moveToFirst()) {

            ma = c.getString(0);
            c.close();
        }
        return ma;
    }
    public String getmasach (String tensach){
        String masach = "";
        String sql = "select MaSach from Sach where TenSach=?";
        Cursor c = database.rawQuery(sql, new String[]{tensach});
        if (c != null && c.moveToFirst()) {

            masach = c.getString(0);
            c.close();
        }
        return masach;
    }
    public long insert_muonsach( MyMuonSach obj){
        int sl=0;
        String sql = "select SoLuong from MuonSach where MaQuanLy=? and MaSach=? and NgayMuon=? and TinhTrang=?";
        ContentValues values = new ContentValues();
        Cursor c = database.rawQuery(sql, new String[]{String.valueOf(obj.getMaquanly()),String.valueOf(obj.getMasach()), obj.getNgaymuon(), "Chưa trả"});
        if(c!=null && c.moveToFirst()){
            sl=c.getInt(0);
            values.put("SoLuong",obj.getSoluong()+sl);
            return database.update("MuonSach",values,"MaQuanLy=? AND MaSach=? AND NgayMuon=? AND TinhTrang=?",new String[]{String.valueOf(obj.getMaquanly()), String.valueOf(obj.getMasach()), obj.getNgaymuon(), "Chưa trả"});
        }
        else{
            values.put("MaQuanLy", obj.getMaquanly());
            values.put("MaSach", obj.getMasach());
            values.put("NgayMuon",obj.getNgaymuon());
            values.put("NgayTra","");
            values.put("TinhTrang","Chưa trả");
            values.put("SoLuong", obj.getSoluong());
            values.put("PhuPhi",0);
            return database.insert("MuonSach",null, values);
        }
    }
    public Date stringtoDate (String date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date1 = null;
        try{
            date1 = sdf.parse(date);
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        return date1;
    }
}
