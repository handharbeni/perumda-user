package com.mhandharbeni.perumda.room.entity.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class DataLoket {
    @PrimaryKey
    @NonNull
    @SerializedName("loket")
    @Expose
    private String loket;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("gpslon")
    @Expose
    private String gpslon;
    @SerializedName("gpslat")
    @Expose
    private String gpslat;

    public String getLoket() {
        return loket;
    }

    public void setLoket(String loket) {
        this.loket = loket;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGpslon() {
        return gpslon;
    }

    public void setGpslon(String gpslon) {
        this.gpslon = gpslon;
    }

    public String getGpslat() {
        return gpslat;
    }

    public void setGpslat(String gpslat) {
        this.gpslat = gpslat;
    }
}
