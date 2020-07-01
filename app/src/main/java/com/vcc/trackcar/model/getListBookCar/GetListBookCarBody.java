package com.vcc.trackcar.model.getListBookCar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.vcc.trackcar.model.auth.AuthenticationInfo;

public class GetListBookCarBody {

    @SerializedName("authenticationInfo")
    @Expose
    private AuthenticationInfo authenticationInfo;
    @SerializedName("sysUserId")
    @Expose
    private Integer sysUserId;
    @SerializedName("typeMenu")
    @Expose
    private Integer typeMenu;

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("loginName")
    @Expose
    private String loginName;

    public GetListBookCarBody(AuthenticationInfo authenticationInfo, Integer sysUserId, Integer typeMenu, String email, String loginName) {
        this.authenticationInfo = authenticationInfo;
        this.sysUserId = sysUserId;
        this.typeMenu = typeMenu;
        this.email = email;
        this.loginName = loginName;
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

    public Integer getTypeMenu() {
        return typeMenu;
    }

    public void setTypeMenu(Integer typeMenu) {
        this.typeMenu = typeMenu;
    }

}
