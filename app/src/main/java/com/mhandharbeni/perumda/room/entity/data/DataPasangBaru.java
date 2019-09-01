package com.mhandharbeni.perumda.room.entity.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataPasangBaru {
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("noktp")
    @Expose
    private String noktp;
    @SerializedName("nohandphone")
    @Expose
    private String nohandphone;
    @SerializedName("lokasipasang")
    @Expose
    private String lokasipasang;
    @SerializedName("fotoktp")
    @Expose
    private String fotoktp;
    @SerializedName("status")
    @Expose
    private String status;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoktp() {
        return noktp;
    }

    public void setNoktp(String noktp) {
        this.noktp = noktp;
    }

    public String getNohandphone() {
        return nohandphone;
    }

    public void setNohandphone(String nohandphone) {
        this.nohandphone = nohandphone;
    }

    public String getLokasipasang() {
        return lokasipasang;
    }

    public void setLokasipasang(String lokasipasang) {
        this.lokasipasang = lokasipasang;
    }

    public String getFotoktp() {
        return fotoktp;
    }

    public void setFotoktp(String fotoktp) {
        this.fotoktp = fotoktp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
