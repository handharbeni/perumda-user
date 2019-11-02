package com.mhandharbeni.perumda.room.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mhandharbeni.perumda.room.entity.data.DataBerita;

import java.util.List;

public class ResponseBerita {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("detail")
    @Expose
    private List<DataBerita> dataBerita = null;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBerita> getDataBerita() {
        return dataBerita;
    }

    public void setDataBerita(List<DataBerita> dataBerita) {
        this.dataBerita = dataBerita;
    }
}
