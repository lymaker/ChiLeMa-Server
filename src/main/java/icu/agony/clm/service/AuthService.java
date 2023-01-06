package icu.agony.clm.service;

import icu.agony.clm.controller.auth.param.LoginParam;
import icu.agony.clm.controller.auth.param.RegisterParam;
import icu.agony.clm.controller.user.vo.UserVO;

public interface AuthService {

    /**
     * 用户登录
     *
     * @param param 登录参数
     */
    void login(LoginParam param);

    /**
     * 用户注册
     *
     * @param param 注册参数
     */
    void register(RegisterParam param);

    /**
     * 退出登录
     */
    void logout();

    /**
     * 登录检查
     */
    void check();

    /**
     * 登录用户信息
     *
     * @return 返回给前端的数据
     */
    UserVO userInfo();

}
