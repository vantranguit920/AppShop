package com.example.dell.appstore.model;

/**
 * Created by Dell on 25-Nov-17.
 */

public class loai_sp {
    public int id;
    public String ten;

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

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String hinh;

    public loai_sp(int id, String ten, String hinh) {
        this.id = id;
        this.ten = ten;
        this.hinh = hinh;
    }
}
