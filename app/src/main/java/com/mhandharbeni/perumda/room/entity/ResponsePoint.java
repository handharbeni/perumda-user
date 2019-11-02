package com.mhandharbeni.perumda.room.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mhandharbeni.perumda.room.entity.data.DataPoint;

public class ResponsePoint {
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private DataPoint data;

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

    public DataPoint getData() {
        return data;
    }

    public void setData(DataPoint data) {
        this.data = data;
    }
}