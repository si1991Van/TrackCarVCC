package com.vcc.trackcar.model.closeBookCar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vcc.trackcar.model.addBookCar.SysUserRequest;
import com.vcc.trackcar.model.getListBookCar.LstBookCarDto;

public class CloseBookCarBody {

    @SerializedName("bookCarDto")
    @Expose
    private LstBookCarDto bookCarClose;
    @SerializedName("sysUserRequest")
    @Expose
    private SysUserRequest sysUserRequest;

    public LstBookCarDto getBookCarClose() {
        return bookCarClose;
    }

    public void setBookCarClose(LstBookCarDto bookCarClose) {
        this.bookCarClose = bookCarClose;
    }

    public SysUserRequest getSysUserRequest() {
        return sysUserRequest;
    }

    public void setSysUserRequest(SysUserRequest sysUserRequest) {
        this.sysUserRequest = sysUserRequest;
    }

}