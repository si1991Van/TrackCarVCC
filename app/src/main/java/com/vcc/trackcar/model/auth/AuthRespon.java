package com.vcc.trackcar.model.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthRespon {

    @SerializedName("lstSysUserComDTO")
    @Expose
    private Object lstSysUserComDTO;
    @SerializedName("resultInfo")
    @Expose
    private ResultInfo resultInfo;
    @SerializedName("userLogin")
    @Expose
    private UserLogin userLogin;
    @SerializedName("user")
    @Expose
    private Object user;
    @SerializedName("listUser")
    @Expose
    private Object listUser;

    public Object getLstSysUserComDTO() {
        return lstSysUserComDTO;
    }

    public void setLstSysUserComDTO(Object lstSysUserComDTO) {
        this.lstSysUserComDTO = lstSysUserComDTO;
    }

    public ResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }

    public UserLogin getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(UserLogin userLogin) {
        this.userLogin = userLogin;
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }

    public Object getListUser() {
        return listUser;
    }

    public void setListUser(Object listUser) {
        this.listUser = listUser;
    }

}
