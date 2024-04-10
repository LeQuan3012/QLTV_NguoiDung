package com.example.navigationcustom2.Sach;

public class MySach {
    private int maSach;
    private String tenSach;
    private int maTheLoai;
    private String tomTat;
    private int matacGia;
    private String ngayXuatBan;
    private int manxb;
    private int soLuong;
    private byte[] hinhAnh;
    public MySach(){

    }

    public MySach(int maSach, String tenSach, int maTheLoai, String tomTat, int matacGia, String ngayXuatBan, int manxb, int soLuong, byte[] hinhAnh) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.maTheLoai = maTheLoai;
        this.tomTat = tomTat;
        this.matacGia = matacGia;
        this.ngayXuatBan = ngayXuatBan;
        this.manxb = manxb;
        this.soLuong = soLuong;
        this.hinhAnh = hinhAnh;
    }
    public MySach(String tenSach, int maTheLoai, String tomTat, int matacGia, String ngayXuatBan, int manxb, int soLuong, byte[] hinhAnh) {
        this.tenSach = tenSach;
        this.maTheLoai = maTheLoai;
        this.tomTat = tomTat;
        this.matacGia = matacGia;
        this.ngayXuatBan = ngayXuatBan;
        this.manxb = manxb;
        this.soLuong = soLuong;
        this.hinhAnh = hinhAnh;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getMaTheLoai() {
        return maTheLoai;
    }

    public void setMaTheLoai(int maTheLoai) {
        this.maTheLoai = maTheLoai;
    }

    public String getTomTat() {
        return tomTat;
    }

    public void setTomTat(String tomTat) {
        this.tomTat = tomTat;
    }

    public int getMatacGia() {
        return matacGia;
    }

    public void setMatacGia(int matacGia) {
        this.matacGia = matacGia;
    }

    public String getNgayXuatBan() {
        return ngayXuatBan;
    }

    public void setNgayXuatBan(String ngayXuatBan) {
        this.ngayXuatBan = ngayXuatBan;
    }

    public int getManxb() {
        return manxb;
    }

    public void setManxb(int manxb) {
        this.manxb = manxb;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
