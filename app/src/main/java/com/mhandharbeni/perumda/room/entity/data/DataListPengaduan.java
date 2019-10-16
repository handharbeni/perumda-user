package com.mhandharbeni.perumda.room.entity.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataListPengaduan implements Serializable {
    @SerializedName("nopengaduan")
    @Expose
    private String nopengaduan;
    @SerializedName("kdunit")
    @Expose
    private String kdunit;
    @SerializedName("nosal")
    @Expose
    private String nosal;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("golongan")
    @Expose
    private String golongan;
    @SerializedName("desa")
    @Expose
    private String desa;
    @SerializedName("jenispengaduan")
    @Expose
    private String jenispengaduan;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("gps")
    @Expose
    private String gps;
    @SerializedName("tglpengaduan")
    @Expose
    private String tglpengaduan;
    @SerializedName("jampengaduan")
    @Expose
    private String jampengaduan;
    @SerializedName("isipengaduan")
    @Expose
    private String isipengaduan;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("namapelapor")
    @Expose
    private String namapelapor;
    @SerializedName("alamatpelapor")
    @Expose
    private String alamatpelapor;
    @SerializedName("hppelapor")
    @Expose
    private String hppelapor;

    public String getNopengaduan() {
        return nopengaduan;
    }

    public void setNopengaduan(String nopengaduan) {
        this.nopengaduan = nopengaduan;
    }

    public String getKdunit() {
        return kdunit;
    }

    public void setKdunit(String kdunit) {
        this.kdunit = kdunit;
    }

    public String getNosal() {
        return nosal;
    }

    public void setNosal(String nosal) {
        this.nosal = nosal;
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

    public String getGolongan() {
        return golongan;
    }

    public void setGolongan(String golongan) {
        this.golongan = golongan;
    }

    public String getDesa() {
        return desa;
    }

    public void setDesa(String desa) {
        this.desa = desa;
    }

    public String getJenispengaduan() {
        return jenispengaduan;
    }

    public void setJenispengaduan(String jenispengaduan) {
        this.jenispengaduan = jenispengaduan;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTglpengaduan() {
        return tglpengaduan;
    }

    public void setTglpengaduan(String tglpengaduan) {
        this.tglpengaduan = tglpengaduan;
    }

    public String getJampengaduan() {
        return jampengaduan;
    }

    public void setJampengaduan(String jampengaduan) {
        this.jampengaduan = jampengaduan;
    }

    public String getIsipengaduan() {
        return isipengaduan;
    }

    public void setIsipengaduan(String isipengaduan) {
        this.isipengaduan = isipengaduan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNamapelapor() {
        return namapelapor;
    }

    public void setNamapelapor(String namapelapor) {
        this.namapelapor = namapelapor;
    }

    public String getAlamatpelapor() {
        return alamatpelapor;
    }

    public void setAlamatpelapor(String alamatpelapor) {
        this.alamatpelapor = alamatpelapor;
    }

    public String getHppelapor() {
        return hppelapor;
    }

    public void setHppelapor(String hppelapor) {
        this.hppelapor = hppelapor;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }
}
