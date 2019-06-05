package com.example.dell.appstore.model;

/**
 * Created by Dell on 03-Dec-17.
 */

public class giohang {
    int idsp;
    String tensp;
    int gia;
    int soluong;
    String hinh;

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public giohang(int idsp, String tensp, int gia, int soluong, String hinh) {
        this.idsp = idsp;
        this.tensp = tensp;
        this.gia = gia;
        this.soluong = soluong;
        this.hinh = hinh;
    }
}
