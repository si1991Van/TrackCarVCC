package com.vcc.trackcar.model.getListCar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vcc.trackcar.model.auth.ResultInfo;
import com.vcc.trackcar.model.getListDriverCar.LstBookCarDto;

import java.util.List;

public class GetListCarRespon {
    @SerializedName("resultInfo")
    @Expose
    private ResultInfo resultInfo;
    @SerializedName("lstBookCarDto")
    @Expose
    private List<LstBookCarDto> lstBookCarDto = null;

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public List<LstBookCarDto> getLstBookCarDto() {
        return lstBookCarDto;
    }

    public void setLstBookCarDto(List<LstBookCarDto> lstBookCarDto) {
        this.lstBookCarDto = lstBookCarDto;
    }
}
