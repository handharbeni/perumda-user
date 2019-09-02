package com.mhandharbeni.perumda.room.entity.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataProgressPengaduan {
    @SerializedName("no")
    @Expose
    private Integer no;
    @SerializedName("tindakan")
    @Expose
    private String tindakan;
    @SerializedName("tgl_tindakan")
    @Expose
    private String tgl_tindakan;
    @SerializedName("jam_tindakan")
    @Expose
    private String jam_tindakan;
    @SerializedName("status")
    @Expose
    private String status;

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getTindakan() {
        return tindakan;
    }

    public void setTindakan(String tindakan) {
        this.tindakan = tindakan;
    }

    public String getTgl_tindakan() {
        return tgl_tindakan;
    }

    public void setTgl_tindakan(String tgl_tindakan) {
        this.tgl_tindakan = tgl_tindakan;
    }

    public String getJam_tindakan() {
        return jam_tindakan;
    }

    public void setJam_tindakan(String jam_tindakan) {
        this.jam_tindakan = jam_tindakan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
