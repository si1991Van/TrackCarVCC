package com.vcc.trackcar.model.addBookCar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vcc.trackcar.model.auth.AuthenticationInfo;

public class SysUserRequest {

    @SerializedName("authenticationInfo")
    @Expose
    private AuthenticationInfo authenticationInfo;
    @SerializedName("flag")
    @Expose
    private Integer flag;
    @SerializedName("sysUserId")
    @Expose
    private Integer sysUserId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("loginName")
    @Expose
    private String loginName;

    public AuthenticationInfo getAuthenticationInfo() {
        return authenticationInfo;
    }

    public void setAuthenticationInfo(AuthenticationInfo authenticationInfo) {
        this.authenticationInfo = authenticationInfo;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
