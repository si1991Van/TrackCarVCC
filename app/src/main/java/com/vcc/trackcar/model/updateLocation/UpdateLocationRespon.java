package com.vcc.trackcar.model.updateLocation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vcc.trackcar.model.auth.ResultInfo;

public class UpdateLocationRespon {
    @SerializedName("resultInfo")
    @Expose
    private ResultInfo resultInfo;

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }
}
