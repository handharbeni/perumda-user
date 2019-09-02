package com.mhandharbeni.perumda.room.entity.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataGangguan implements Serializable {
    @SerializedName("id_gangguan")
    @Expose
    private String idGangguan;
    @SerializedName("judul_gangguan")
    @Expose
    private String judulGangguan;
    @SerializedName("ket_gangguan")
    @Expose
    private String ketGangguan;
    @SerializedName("unit_gangguan")
    @Expose
    private String unitGangguan;
    @SerializedName("wil_terdampak")
    @Expose
    private String wilTerdampak;
    @SerializedName("tgl_gangguan")
    @Expose
    private String tglGangguan;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("status")
    @Expose
    private String status;

    public String getIdGangguan() {
        return idGangguan;
    }

    public void setIdGangguan(String idGangguan) {
        this.idGangguan = idGangguan;
    }

    public String getJudulGangguan() {
        return judulGangguan;
    }

    public void setJudulGangguan(String judulGangguan) {
        this.judulGangguan = judulGangguan;
    }

    public String getKetGangguan() {
        return ketGangguan;
    }

    public void setKetGangguan(String ketGangguan) {
        this.ketGangguan = ketGangguan;
    }

    public String getUnitGangguan() {
        return unitGangguan;
    }

    public void setUnitGangguan(String unitGangguan) {
        this.unitGangguan = unitGangguan;
    }

    public String getWilTerdampak() {
        return wilTerdampak;
    }

    public void setWilTerdampak(String wilTerdampak) {
        this.wilTerdampak = wilTerdampak;
    }

    public String getTglGangguan() {
        return tglGangguan;
    }

    public void setTglGangguan(String tglGangguan) {
        this.tglGangguan = tglGangguan;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
