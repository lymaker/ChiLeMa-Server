package icu.agony.clm.service.impl;

import icu.agony.clm.exception.InternalServerException;
import icu.agony.clm.mapper.UserRoleMapper;
import icu.agony.clm.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "user-role")
@RequiredArgsConstructor
@Slf4j
public class UserRoleServiceImpl implements UserRoleService {

    private final boolean isDebug = log.isDebugEnabled();

    private final UserRoleMapper userRoleMapper;

    @Override
    public void create(String userId, Integer roleId) {
        if (isDebug) {
            log.debug("添加用户角色 -> userId：[{}]，roleId：[{}]", userId, roleId);
        }
        try {
            userRoleMapper.insert(userId, roleId);
        } catch (Exception e) {
            throw new InternalServerException("创建用户角色失败", e);
        }
    }

    @Override
    @CacheEvict(key = "#userId")
    public void delete(String userId, Integer roleId) {
        if (isDebug) {
            log.debug("删除用户角色 -> userId：[{}]，roleId：[{}]", userId, roleId);
        }
        try {
            userRoleMapper.delete(userId, roleId);
        } catch (Exception e) {
            throw new InternalServerException("删除用户角色失败", e);
        }
    }

    @Override
    @Cacheable(key = "#userId")
    public List<String> select(String userId) {
        List<String> list = userRoleMapper.select(userId);
        if (isDebug) {
            log.debug("查询用户角色 -> user：[{}]，result：{}", userId, list);
        }
        return list;
    }

}
