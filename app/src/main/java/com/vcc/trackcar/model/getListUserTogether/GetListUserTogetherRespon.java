package com.vcc.trackcar.model.getListUserTogether;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vcc.trackcar.model.auth.ResultInfo;
import com.vcc.trackcar.model.getListUser.LstUserDto;

import java.util.List;

public class GetListUserTogetherRespon {
    @SerializedName("resultInfo")
    @Expose
    private ResultInfo resultInfo;
    @SerializedName("lstBookCarDto")
    @Expose
    private List<LstUserDto> lstUserDtoList = null;
    @SerializedName("bookCarDto")
    @Expose
    private Object bookCarDto;

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public List<LstUserDto> getLstBookCarDto() {
        return lstUserDtoList;
    }

    public void setLstBookCarDto(List<LstUserDto> lstUserDtoList) {
        this.lstUserDtoList = lstUserDtoList;
    }

    public Object getBookCarDto() {
        return bookCarDto;
    }

    public void setBookCarDto(Object bookCarDto) {
        this.bookCarDto = bookCarDto;
    }
}
