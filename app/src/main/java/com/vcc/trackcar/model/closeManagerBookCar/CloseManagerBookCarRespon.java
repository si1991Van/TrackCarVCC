package com.vcc.trackcar.model.closeManagerBookCar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vcc.trackcar.model.auth.ResultInfo;

public class CloseManagerBookCarRespon {
    @SerializedName("resultInfo")
    @Expose
    private ResultInfo resultInfo;
    @SerializedName("lstBookCarDto")
    @Expose
    private Object lstBookCarDto;
    @SerializedName("bookCarDto")
    @Expose
    private Object bookCarDto;

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public Object getLstBookCarDto() {
        return lstBookCarDto;
    }

    public void setLstBookCarDto(Object lstBookCarDto) {
        this.lstBookCarDto = lstBookCarDto;
    }

    public Object getBookCarDto() {
        return bookCarDto;
    }

    public void setBookCarDto(Object bookCarDto) {
        this.bookCarDto = bookCarDto;
    }
}
