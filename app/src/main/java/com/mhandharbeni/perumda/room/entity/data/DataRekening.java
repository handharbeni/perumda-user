package com.mhandharbeni.perumda.room.entity.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataRekening {
    @SerializedName("id_pelanggan")
    @Expose
    private String idPelanggan;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("gpslong")
    @Expose
    private String gpslong;
    @SerializedName("gpslat")
    @Expose
    private String gpslat;
    @SerializedName("kdgol")
    @Expose
    private String kdgol;
    @SerializedName("golongan")
    @Expose
    private String golongan;
    @SerializedName("kddesa")
    @Expose
    private String kddesa;
    @SerializedName("desa")
    @Expose
    private String desa;
    @SerializedName("kdunit")
    @Expose
    private String kdunit;
    @SerializedName("unit")
    @Expose
    private String unit;

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
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

    public String getKdgol() {
        return kdgol;
    }

    public void setKdgol(String kdgol) {
        this.kdgol = kdgol;
    }

    public String getGolongan() {
        return golongan;
    }

    public void setGolongan(String golongan) {
        this.golongan = golongan;
    }

    public String getKddesa() {
        return kddesa;
    }

    public void setKddesa(String kddesa) {
        this.kddesa = kddesa;
    }

    public String getDesa() {
        return desa;
    }

    public void setDesa(String desa) {
        this.desa = desa;
    }

    public String getKdunit() {
        return kdunit;
    }

    public void setKdunit(String kdunit) {
        this.kdunit = kdunit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
