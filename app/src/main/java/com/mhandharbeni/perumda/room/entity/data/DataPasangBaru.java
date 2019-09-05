package com.mhandharbeni.perumda.room.entity.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataPasangBaru {

    @SerializedName("id")
    @Expose
    private String id;
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
    @SerializedName("gpslong")
    @Expose
    private String gpslong;
    @SerializedName("gpslat")
    @Expose
    private String gpslat;
    @SerializedName("fotoktp")
    @Expose
    private Object fotoktp;
    @SerializedName("status")
    @Expose
    private Object status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getGpslong() {
        return gpslong;
    }

    public void setGpslong(String gpslong) {
        this.gpslong = gpslong;
    }

    public String getGpslat() {
        return gpslat;
    }

    public void setGpslat(String gpslat) {
        this.gpslat = gpslat;
    }

    public Object getFotoktp() {
        return fotoktp;
    }

    public void setFotoktp(Object fotoktp) {
        this.fotoktp = fotoktp;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }
}

