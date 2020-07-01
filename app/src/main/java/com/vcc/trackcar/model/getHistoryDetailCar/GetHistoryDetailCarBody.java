package com.vcc.trackcar.model.getHistoryDetailCar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vcc.trackcar.model.addBookCar.SysUserRequest;
import com.vcc.trackcar.model.getHistoryCar.BookCarHistory;

public class GetHistoryDetailCarBody {
    @SerializedName("bookCarDto")
    @Expose
    private BookCarHistory bookCarDto;
    @SerializedName("sysUserRequest")
    @Expose
    private SysUserRequest sysUserRequest;

    public BookCarHistory getBookCarDto() {
        return bookCarDto;
    }

    public void setBookCarDto(BookCarHistory bookCarDto) {
        this.bookCarDto = bookCarDto;
    }

    public SysUserRequest getSysUserRequest() {
        return sysUserRequest;
    }

    public void setSysUserRequest(SysUserRequest sysUserRequest) {
        this.sysUserRequest = sysUserRequest;
    }
}
