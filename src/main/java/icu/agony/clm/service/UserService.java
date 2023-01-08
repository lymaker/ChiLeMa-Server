package icu.agony.clm.service;

import icu.agony.clm.controller.user.param.UserCreateParam;
import icu.agony.clm.controller.user.param.UserUpdateParam;
import icu.agony.clm.entity.UserEntity;

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
    void update(UserUpdateParam param);

    /**
     * 查询当前登录用户
     *
     * @return 用户实体
     */
    UserEntity select();

    /**
     * 通过 id 查询指定用户
     *
     * @param id 用户id
     * @return 用户实体
     */
    UserEntity selectById(String id);

    /**
     * 创建用户
     *
     * @param param 请求参数
     */
    void create(UserCreateParam param);

}
