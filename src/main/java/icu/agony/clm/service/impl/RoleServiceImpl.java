package icu.agony.clm.service.impl;

import icu.agony.clm.entity.RoleEntity;
import icu.agony.clm.mapper.RoleMapper;
import icu.agony.clm.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;

    @Override
    public List<RoleEntity> selectAll() {
        return roleMapper.selectList(null);
    }

}
