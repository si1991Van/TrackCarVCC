package com.vcc.trackcar.model.getBookCarToken;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vcc.trackcar.model.addBookCar.SysUserRequest;
import com.vcc.trackcar.model.getListBookCar.LstBookCarDto;

public class GetBookCarTokenBody {
    @SerializedName("bookCarDto")
    @Expose
    private LstBookCarDto bookCarDto;
    @SerializedName("sysUserRequest")
    @Expose
    private SysUserRequest sysUserRequest;

    public LstBookCarDto getBookCarDto() {
        return bookCarDto;
    }

    public void setBookCarDto(LstBookCarDto bookCarDto) {
        this.bookCarDto = bookCarDto;
    }

    public SysUserRequest getSysUserRequest() {
        return sysUserRequest;
    }

    public void setSysUserRequest(SysUserRequest sysUserRequest) {
        this.sysUserRequest = sysUserRequest;
    }

}