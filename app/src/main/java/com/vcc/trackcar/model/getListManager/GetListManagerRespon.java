package com.vcc.trackcar.model.getListManager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vcc.trackcar.model.auth.ResultInfo;
import com.vcc.trackcar.model.getListUser.LstUserDto;

import java.util.List;

public class GetListManagerRespon {
    @SerializedName("resultInfo")
    @Expose
    private ResultInfo resultInfo;
    @SerializedName("lstBookCarDto")
    @Expose
    private List<LstUserDto> lstBookCarDto = null;

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public List<LstUserDto> getLstBookCarDto() {
        return lstBookCarDto;
    }

    public void setLstBookCarDto(List<LstUserDto> lstBookCarDto) {
        this.lstBookCarDto = lstBookCarDto;
    }
}
