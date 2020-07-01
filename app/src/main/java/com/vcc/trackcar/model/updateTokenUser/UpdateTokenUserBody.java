package com.vcc.trackcar.model.updateTokenUser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vcc.trackcar.model.auth.AuthenticationInfo;

public class UpdateTokenUserBody {
    @SerializedName("authenticationInfo")
    @Expose
    private AuthenticationInfo authenticationInfo;
    @SerializedName("sysUserId")
    @Expose
    private Integer sysUserId;
    @SerializedName("token")
    @Expose
    private String token;

    public AuthenticationInfo getAuthenticationInfo() {
        return authenticationInfo;
    }

    public void setAuthenticationInfo(AuthenticationInfo authenticationInfo) {
        this.authenticationInfo = authenticationInfo;
    }

    public Integer getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Integer sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
