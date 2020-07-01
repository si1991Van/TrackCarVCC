package com.vcc.trackcar.model.getBookCarToken;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vcc.trackcar.model.addBookCar.SysUserRequest;
import com.vcc.trackcar.model.auth.ResultInfo;
import com.vcc.trackcar.model.getListBookCar.LstBookCarDto;

import java.util.List;

public class GetBookCarTokenRespon {
    @SerializedName("resultInfo")
    @Expose
    private ResultInfo resultInfo;
    @SerializedName("lstBookCarDto")
    @Expose
    private List<LstBookCarDto> lstBookCarDto = null;
    @SerializedName("bookCarDto")
    @Expose
    private Object bookCarDto;

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

    public Object getBookCarDto() {
        return bookCarDto;
    }

    public void setBookCarDto(Object bookCarDto) {
        this.bookCarDto = bookCarDto;
    }

}
