package icu.agony.clm.service;

import icu.agony.clm.controller.auth.param.LoginParam;

public interface AuthService {

    /**
     * 用户登录
     *
     * @param param 登录参数
     */
    void login(LoginParam param);

    /**
     * 退出登录
     */
    void logout();

    /**
     * 登录检查
     */
    void check();

}
