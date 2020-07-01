package com.vcc.trackcar.model.getAppVersion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vcc.trackcar.model.auth.ResultInfo;

import java.util.List;

public class GetAppVersionRespon {
    @SerializedName("resultInfo")
    @Expose
    private ResultInfo resultInfo;
    @SerializedName("lstBookCarDto")
    @Expose
    private List<GetAppVersion> lstBookCarDto = null;

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public List<GetAppVersion> getLstBookCarDto() {
        return lstBookCarDto;
    }

    public void setLstBookCarDto(List<GetAppVersion> lstBookCarDto) {
        this.lstBookCarDto = lstBookCarDto;
    }
}
