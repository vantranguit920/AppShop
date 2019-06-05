package com.example.dell.appstore.model;

import java.io.Serializable;

/**
 * Created by Dell on 28-Nov-17.
 */

public class sanpham implements Serializable{
    int id;
    String ten;
    float giá;
    String chitiet;

    public sanpham(int id, String ten, float giá, String chitiet, String hinh, int idloai, int giamgia) {
        this.id = id;
        this.ten = ten;
        this.giá = giá;
        this.chitiet = chitiet;
        this.hinh = hinh;
        this.idloai = idloai;
        this.giamgia = giamgia;
    }

    String hinh;
    int idloai;
    int giamgia;

    public int getGiamgia() {
        return giamgia;
    }

    public void setGiamgia(int giamgia) {
        this.giamgia = giamgia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public float getGiá() {
        return giá;
    }

    public void setGiá(float giá) {
        this.giá = giá;
    }

    public String getChitiet() {
        return chitiet;
    }

    public void setChitiet(String chitiet) {
        this.chitiet = chitiet;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public int getIdloai() {
        return idloai;
    }

    public void setIdloai(int idloai) {
        this.idloai = idloai;
    }

    public sanpham(int id, String ten, float giá, String chitiet, String hinh, int idloai) {
        this.id = id;
        this.ten = ten;
        this.giá = giá;
        this.chitiet = chitiet;
        this.hinh = hinh;
        this.idloai = idloai;
    }
}
