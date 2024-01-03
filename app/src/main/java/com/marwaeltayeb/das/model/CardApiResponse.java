package com.marwaeltayeb.das.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CardApiResponse {

    @SerializedName("msg")
    private String msg;
    @SerializedName("success")
    private boolean success;

    public boolean getStatus() {
        return success;
    }
    public String getMsg() {
        return msg;
    }
}
