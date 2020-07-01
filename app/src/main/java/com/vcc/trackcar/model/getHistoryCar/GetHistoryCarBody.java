package com.vcc.trackcar.model.getHistoryCar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vcc.trackcar.model.addBookCar.SysUserRequest;

public class GetHistoryCarBody {
    @SerializedName("bookCarDto")
    @Expose
    private CarInfoHistory bookCarDto;
    @SerializedName("sysUserRequest")
    @Expose
    private SysUserRequest sysUserRequest;

    public CarInfoHistory getBookCarDto() {
        return bookCarDto;
    }

    public void setBookCarDto(CarInfoHistory bookCarDto) {
        this.bookCarDto = bookCarDto;
    }

    public SysUserRequest getSysUserRequest() {
        return sysUserRequest;
    }

    public void setSysUserRequest(SysUserRequest sysUserRequest) {
        this.sysUserRequest = sysUserRequest;
    }
}
