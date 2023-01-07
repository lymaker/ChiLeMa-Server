package icu.agony.clm.service;

import icu.agony.clm.controller.user.param.UserUpdateParam;

public interface UserService {

    /**
     * 检测用户名是否可用
     *
     * @param username 用户名
     */
    void checkUsername(String username);

    /**
     * 更新用户字段
     *
     * @param param 请求参数
     */
    void updateField(UserUpdateParam param);

}
