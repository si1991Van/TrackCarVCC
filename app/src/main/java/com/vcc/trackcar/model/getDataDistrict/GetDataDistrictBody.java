package com.vcc.trackcar.model.getDataDistrict;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vcc.trackcar.model.addBookCar.SysUserRequest;

public class GetDataDistrictBody {

    @SerializedName("bookCarDto")
    @Expose
    private BodyDataDistrict bookCarDto;
    @SerializedName("sysUserRequest")
    @Expose
    private SysUserRequest sysUserRequest;

    public BodyDataDistrict getBookCarDto() {
        return bookCarDto;
    }

    public void setBookCarDto(BodyDataDistrict bookCarDto) {
        this.bookCarDto = bookCarDto;
    }

    public SysUserRequest getSysUserRequest() {
        return sysUserRequest;
    }

    public void setSysUserRequest(SysUserRequest sysUserRequest) {
        this.sysUserRequest = sysUserRequest;
    }

}
