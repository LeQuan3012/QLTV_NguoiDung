package com.example.navigationcustom2.KeSach;

public class MyKeSach {
    private int MaTheLoai;
    private String TenTheLoai;

    public MyKeSach() {
    }

    public MyKeSach(int maTheLoai, String tenTheLoai) {
        MaTheLoai = maTheLoai;
        TenTheLoai = tenTheLoai;
    }

    public int getMaTheLoai() {
        return MaTheLoai;
    }

    public void setMaTheLoai(int maTheLoai) {
        MaTheLoai = maTheLoai;
    }

    public String getTenTheLoai() {
        return TenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        TenTheLoai = tenTheLoai;
    }

}
