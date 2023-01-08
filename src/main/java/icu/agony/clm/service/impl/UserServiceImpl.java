package icu.agony.clm.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import icu.agony.clm.config.properties.ClmAuthProperties;
import icu.agony.clm.config.properties.ClmDefaultProperties;
import icu.agony.clm.consts.Role;
import icu.agony.clm.controller.user.param.UserCreateParam;
import icu.agony.clm.controller.user.param.UserUpdateParam;
import icu.agony.clm.entity.UserEntity;
import icu.agony.clm.entity.UserRoleEntity;
import icu.agony.clm.exception.BadRequestException;
import icu.agony.clm.exception.InternalServerException;
import icu.agony.clm.mapper.UserMapper;
import icu.agony.clm.mapper.UserRoleMapper;
import icu.agony.clm.service.UserService;
import icu.agony.clm.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@CacheConfig(cacheNames = "user")
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final boolean isDebug = log.isDebugEnabled();

    private final ClmAuthProperties clmAuthProperties;

    private final ClmDefaultProperties clmDefaultProperties;

    private final UserMapper userMapper;

    private final UserRoleMapper userRoleMapper;

    private final ModelMapper modelMapper;

    @Override
    public void checkUsername(String username) {
        LambdaQueryWrapper<UserEntity> qw = new LambdaQueryWrapper<>();
        qw.eq(UserEntity::getUsername, username);
        if (userMapper.exists(qw)) {
            BadRequestException badRequestException = new BadRequestException("用户名不可用");
            badRequestException.message("此用户名已被使用");
            throw badRequestException;
        }
    }

    @Override
    @CacheEvict(key = "target.cacheKey()")
    public void updateField(UserUpdateParam param) {
        try {
            // 过滤无效JSON
            if (ObjectUtil.isEmpty(param)) {
                return;
            }
            UserEntity userEntity = modelMapper.map(param, UserEntity.class);
            userEntity.setId(StpUtil.getLoginIdAsString());
            userMapper.updateById(userEntity);
        } catch (Exception e) {
            BadRequestException badRequestException = new BadRequestException("更新用户信息失败", e);
            badRequestException.message("更新信息失败");
            throw badRequestException;
        }
    }

    @Override
    @Cacheable(key = "target.cacheKey()")
    public UserEntity select() {
        return selectById(StpUtil.getLoginIdAsString());
    }

    @Override
    @Cacheable(key = "target.cacheKey()")
    public UserEntity selectById(String id) {
        return new LambdaQueryChainWrapper<>(userMapper)
            .eq(UserEntity::getId, id)
            .oneOpt()
            .orElseThrow(() -> new BadRequestException("数据库中不存在与此相同的id"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(UserCreateParam param) {
        if (isDebug) {
            log.debug("用户注册参数：{}", param);
        }
        try {
            UserEntity userEntity = modelMapper.map(param, UserEntity.class);
            String encryptPassword = SaSecureUtil.aesEncrypt(clmAuthProperties.getAesKey(), userEntity.getPassword());
            userEntity.setPassword(encryptPassword);
            userEntity.setAvatarImageUrl(clmDefaultProperties.getAvatarImageUrl());
            userMapper.insert(userEntity);
            userRoleMapper.insert(new UserRoleEntity(userEntity.getId(), Role.CONSUMER_ID));
        } catch (Exception e) {
            InternalServerException internalServerException = new InternalServerException("注册失败", e);
            internalServerException.message("用户注册失败");
            throw internalServerException;
        }
    }

    public String cacheKey() {
        return StpUtil.getLoginIdAsString();
    }

}
