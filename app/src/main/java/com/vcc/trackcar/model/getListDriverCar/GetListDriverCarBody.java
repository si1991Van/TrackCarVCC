package com.vcc.trackcar.model.getListDriverCar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vcc.trackcar.model.addBookCar.SysUserRequest;

public class GetListDriverCarBody {

    @SerializedName("bookCarDto")
    @Expose
    private BookCarDto bookCarDto;
    @SerializedName("sysUserRequest")
    @Expose
    private SysUserRequest sysUserRequest;

    public BookCarDto getBookCarDto() {
        return bookCarDto;
    }

    public void setBookCarDto(BookCarDto bookCarDto) {
        this.bookCarDto = bookCarDto;
    }

    public SysUserRequest getSysUserRequest() {
        return sysUserRequest;
    }

    public void setSysUserRequest(SysUserRequest sysUserRequest) {
        this.sysUserRequest = sysUserRequest;
    }

}
