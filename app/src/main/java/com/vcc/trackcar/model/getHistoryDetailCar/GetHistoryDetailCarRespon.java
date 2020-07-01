package com.vcc.trackcar.model.getHistoryDetailCar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vcc.trackcar.model.auth.ResultInfo;
import com.vcc.trackcar.model.getHistoryCar.MakerCar;

import java.util.List;

public class GetHistoryDetailCarRespon {
    @SerializedName("resultInfo")
    @Expose
    private ResultInfo resultInfo;
    @SerializedName("lstBookCarDto")
    @Expose
    private List<MakerCar> lstBookCarDto = null;

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public List<MakerCar> getLstBookCarDto() {
        return lstBookCarDto;
    }

    public void setLstBookCarDto(List<MakerCar> lstBookCarDto) {
        this.lstBookCarDto = lstBookCarDto;
    }
}
