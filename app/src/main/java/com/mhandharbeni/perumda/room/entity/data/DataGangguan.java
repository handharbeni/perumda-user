package com.mhandharbeni.perumda.room.entity.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataGangguan implements Parcelable {
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

    protected DataGangguan(Parcel in) {
        idGangguan = in.readString();
        judulGangguan = in.readString();
        ketGangguan = in.readString();
        unitGangguan = in.readString();
        wilTerdampak = in.readString();
        tglGangguan = in.readString();
        foto = in.readString();
        status = in.readString();
    }

    public static final Creator<DataGangguan> CREATOR = new Creator<DataGangguan>() {
        @Override
        public DataGangguan createFromParcel(Parcel in) {
            return new DataGangguan(in);
        }

        @Override
        public DataGangguan[] newArray(int size) {
            return new DataGangguan[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idGangguan);
        dest.writeString(judulGangguan);
        dest.writeString(ketGangguan);
        dest.writeString(unitGangguan);
        dest.writeString(wilTerdampak);
        dest.writeString(tglGangguan);
        dest.writeString(foto);
        dest.writeString(status);
    }
}
