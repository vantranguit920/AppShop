package com.example.dell.appstore.model;

/**
 * Created by Dell on 13-Dec-17.
 */

public class nhanxet {
    int id;
    int idsp;
    String ten;
    String nhanxet;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNhanxet() {
        return nhanxet;
    }

    public void setNhanxet(String nhanxet) {
        this.nhanxet = nhanxet;
    }

    public nhanxet(int id, int idsp, String ten, String nhanxet) {
        this.id = id;
        this.idsp = idsp;
        this.ten = ten;
        this.nhanxet = nhanxet;
    }
}
