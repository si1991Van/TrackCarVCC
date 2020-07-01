package com.vcc.trackcar.ui.base;

import com.google.gson.Gson;
import com.vcc.trackcar.model.addBookCar.SysUserRequest;
import com.vcc.trackcar.model.auth.AuthenticationInfo;
import com.vcc.trackcar.model.auth.UserLogin;

public class CommonVCC {
    public static final String RESULT_STATUS_OK = "OK";
    public static final String USER_LOGIN = "USER_LOGIN";
    public static final String USER_NAME_LOGIN = "USER_NAME_LOGIN";
    public static boolean debug = false;
    public static final double LIMIT_DISTANCE = 3;

    public static UserLogin getUserLogin() {
        String userLoginPre = PrefManager.getInstance().getString(USER_LOGIN);
        UserLogin userLogin = new Gson().fromJson(userLoginPre, UserLogin.class);
        return userLogin;
    }

    public static SysUserRequest getSysUserRequest(){
        UserLogin userLogin = getUserLogin();
        SysUserRequest sysUserRequest = new SysUserRequest();
        sysUserRequest.setAuthenticationInfo(new AuthenticationInfo(userLogin.getLoginName(), userLogin.getPassword()));
        sysUserRequest.setSysUserId(userLogin.getSysUserId());
        return sysUserRequest;
    }
}
