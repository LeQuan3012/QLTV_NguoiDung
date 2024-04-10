package com.example.navigationcustom2.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.navigationcustom2.KeSach.MyKeSach;
import com.example.navigationcustom2.databases.DB;

import java.util.ArrayList;

public class DAOKeSach {
    private SQLiteDatabase database;
    public DAOKeSach(Context ct){
        DB db = new DB(ct);
        database = db.getWritableDatabase();
    }
    public int getSLSach(int ma){
        int sl = 0;
        String sql = "select count(MaSach) from Sach where MaTheLoai=? group by MaTheLoai";
        Cursor c = database.rawQuery(sql, new String[]{ma+""});
        if(c!=null && c.moveToFirst()){
            sl = c.getInt(0);
        }
        return sl;
    }
    public ArrayList<MyKeSach> getAllLoaiSach(){
        ArrayList<MyKeSach> list = new ArrayList<>();
        String sql = "select * from TheLoaiSach";
        Cursor c = database.rawQuery(sql, null);
        if(c == null){
            c.close();
            return null;
        }
        else{
            while(c.moveToNext()){
                MyKeSach myKeSach = new MyKeSach();
                myKeSach.setMaTheLoai(c.getInt(0));
                myKeSach.setTenTheLoai(c.getString(1));
                list.add(myKeSach);
            }
            c.close();
            return list;
        }
    }

}
