package com.mhandharbeni.perumda.room.entity.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class DataImageSlider {
    @PrimaryKey
    @NonNull
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("keterangan")
    @Expose
    private String keterangan;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("aktif")
    @Expose
    private String aktif;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getAktif() {
        return aktif;
    }

    public void setAktif(String aktif) {
        this.aktif = aktif;
    }
}
