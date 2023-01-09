package icu.agony.clm.service;

import java.util.List;

public interface UserRoleService {

    /**
     * 创建用户角色
     *
     * @param userId 用户id
     * @param roleId 角色id
     */
    void create(String userId, Integer roleId);

    /**
     * 删除用户角色
     *
     * @param userId 用户id
     * @param roleId 角色id
     */
    void delete(String userId, Integer roleId);

    /**
     * 查询用户角色
     *
     * @param userId 用户id
     * @return 用户所拥有的角色
     */
    List<String> select(String userId);

}
