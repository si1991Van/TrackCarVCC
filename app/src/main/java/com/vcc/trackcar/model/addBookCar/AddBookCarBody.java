package com.vcc.trackcar.model.addBookCar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vcc.trackcar.model.getListUser.LstUserDto;

import java.util.List;

public class AddBookCarBody {

    @SerializedName("bookCarDto")
    @Expose
    private BookCarDto bookCarDto;
    @SerializedName("lstPersonTogether")
    @Expose
    private List<LstUserDto> lstPersonTogether = null;
    @SerializedName("sysUserRequest")
    @Expose
    private SysUserRequest sysUserRequest;

    public AddBookCarBody(BookCarDto bookCarDto, List<LstUserDto> lstPersonTogether, SysUserRequest sysUserRequest) {
        this.bookCarDto = bookCarDto;
        this.lstPersonTogether = lstPersonTogether;
        this.sysUserRequest = sysUserRequest;
    }

    public AddBookCarBody(BookCarDto bookCarDto) {
        this.bookCarDto = bookCarDto;
    }

    public BookCarDto getBookCarDto() {
        return bookCarDto;
    }

    public void setBookCarDto(BookCarDto bookCarDto) {
        this.bookCarDto = bookCarDto;
    }

    public List<LstUserDto> getLstPersonTogether() {
        return lstPersonTogether;
    }

    public void setLstPersonTogether(List<LstUserDto> lstPersonTogether) {
        this.lstPersonTogether = lstPersonTogether;
    }

    public SysUserRequest getSysUserRequest() {
        return sysUserRequest;
    }

    public void setSysUserRequest(SysUserRequest sysUserRequest) {
        this.sysUserRequest = sysUserRequest;
    }

}
