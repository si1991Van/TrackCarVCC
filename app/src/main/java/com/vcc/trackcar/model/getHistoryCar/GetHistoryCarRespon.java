package com.vcc.trackcar.model.getHistoryCar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vcc.trackcar.model.auth.ResultInfo;

import java.util.List;

public class GetHistoryCarRespon {
    @SerializedName("resultInfo")
    @Expose
    private ResultInfo resultInfo;
    @SerializedName("lstBookCarDto")
    @Expose
    private List<BookCarHistory> makerCarList = null;

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public List<BookCarHistory> getLstBookCarDto() {
        return makerCarList;
    }

    public void setLstBookCarDto(List<BookCarHistory> makerCarList) {
        this.makerCarList = makerCarList;
    }
}
