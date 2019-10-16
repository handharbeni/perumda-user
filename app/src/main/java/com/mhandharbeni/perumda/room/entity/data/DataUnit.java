package com.mhandharbeni.perumda.room.entity.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class DataUnit {
    @PrimaryKey
    @NonNull
    @SerializedName("kdunit")
    @Expose
    private String kdunit;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("telp")
    @Expose
    private String telp;
    @SerializedName("gpslon")
    @Expose
    private String gpslon;
    @SerializedName("gpslat")
    @Expose
    private String gpslat;

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

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
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
