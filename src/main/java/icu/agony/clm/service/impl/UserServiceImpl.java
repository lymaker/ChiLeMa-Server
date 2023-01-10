package icu.agony.clm.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import icu.agony.clm.config.properties.ClmAuthProperties;
import icu.agony.clm.config.properties.ClmDefaultProperties;
import icu.agony.clm.consts.Role;
import icu.agony.clm.controller.user.param.UserCreateParam;
import icu.agony.clm.controller.user.param.UserSelectParam;
import icu.agony.clm.controller.user.param.UserUpdateParam;
import icu.agony.clm.entity.UserEntity;
import icu.agony.clm.exception.BadRequestException;
import icu.agony.clm.exception.InternalServerException;
import icu.agony.clm.mapper.UserMapper;
import icu.agony.clm.service.UserRoleService;
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

import java.util.Objects;

@Service
@CacheConfig(cacheNames = "user")
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final boolean isDebug = log.isDebugEnabled();

    private final ClmAuthProperties authProperties;

    private final ClmDefaultProperties defaultProperties;

    private final UserMapper userMapper;

    private final ModelMapper modelMapper;

    private final UserRoleService userRoleService;

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
    public void update(UserUpdateParam param) {
        try {
            // 过滤无效JSON
            if (ObjectUtil.isEmpty(param)) {
                return;
            }
            UserEntity userEntity = modelMapper.map(param, UserEntity.class);
            userEntity.setId(StpUtil.getLoginIdAsString());
            userMapper.updateById(userEntity);
        } catch (Exception e) {
            InternalServerException internalServerException = new InternalServerException("更新用户信息失败", e);
            internalServerException.message("更新用户信息失败");
            throw internalServerException;
        }
    }

    @Override
    @Cacheable(key = "target.cacheKey()")
    public UserEntity select() {
        return selectById(StpUtil.getLoginIdAsString());
    }

    @Override
    @Cacheable(key = "#id")
    public UserEntity selectById(String id) {
        return new LambdaQueryChainWrapper<>(userMapper)
            .eq(UserEntity::getId, id)
            .oneOpt()
            .orElseThrow(() -> new BadRequestException("数据库中不存在与此相同的id"));
    }

    @Override
    public UserEntity selectByExample(UserSelectParam param) {
        return new LambdaQueryChainWrapper<>(userMapper)
            .eq(Objects.nonNull(param.getId()), UserEntity::getId, param.getId())
            .eq(Objects.nonNull(param.getNickname()), UserEntity::getNickname, param.getNickname())
            .eq(Objects.nonNull(param.getUsername()), UserEntity::getUsername, param.getUsername())
            .eq(Objects.nonNull(param.getPassword()), UserEntity::getPassword, param.getPassword())
            .eq(Objects.nonNull(param.getPhone()), UserEntity::getPhone, param.getPhone())
            .eq(Objects.nonNull(param.getEmail()), UserEntity::getEmail, param.getEmail())
            .eq(Objects.nonNull(param.getMoney()), UserEntity::getMoney, param.getMoney())
            .oneOpt()
            .orElseThrow(() -> {
                BadRequestException badRequestException = new BadRequestException("未找到匹配的用户");
                badRequestException.message("用户不存在");
                return badRequestException;
            });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(UserCreateParam param) {
        if (isDebug) {
            log.debug("用户注册参数：{}", param);
        }
        try {
            UserEntity userEntity = modelMapper.map(param, UserEntity.class);
            String encryptPassword = SaSecureUtil.aesEncrypt(authProperties.getAesKey(), userEntity.getPassword());
            userEntity.setPassword(encryptPassword);
            userEntity.setAvatarImageUrl(defaultProperties.getAvatarImageUrl());
            userMapper.insert(userEntity);
            userRoleService.create(userEntity.getId(), Role.CONSUMER_ID);
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
