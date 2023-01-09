package icu.agony.clm.service;

import icu.agony.clm.entity.RoleEntity;

import java.util.List;

public interface RoleService {

    /**
     * 查询所有角色
     *
     * @return 所有角色
     */
    List<RoleEntity> selectAll();

}
