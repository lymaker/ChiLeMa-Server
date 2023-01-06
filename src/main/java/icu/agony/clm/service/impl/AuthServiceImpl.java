package icu.agony.clm.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import icu.agony.clm.config.properties.ClmAuthProperties;
import icu.agony.clm.config.properties.ClmDefaultProperties;
import icu.agony.clm.consts.Role;
import icu.agony.clm.controller.auth.param.LoginParam;
import icu.agony.clm.controller.auth.param.RegisterParam;
import icu.agony.clm.controller.user.vo.UserVO;
import icu.agony.clm.entity.UserEntity;
import icu.agony.clm.entity.UserRoleEntity;
import icu.agony.clm.exception.BadRequestException;
import icu.agony.clm.exception.InternalServerException;
import icu.agony.clm.mapper.UserMapper;
import icu.agony.clm.mapper.UserRoleMapper;
import icu.agony.clm.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final boolean isDebug = log.isDebugEnabled();

    private final ClmAuthProperties authProperties;

    private final ClmDefaultProperties defaultProperties;

    private final UserMapper userMapper;

    private final UserRoleMapper userRoleMapper;

    private final ModelMapper modelMapper;

    @Override
    public void login(LoginParam param) {
        if (isDebug) {
            log.debug("用户登录参数：{}", param);
        }
        UserEntity userEntity = new LambdaQueryChainWrapper<>(userMapper)
            .eq(UserEntity::getUsername, param.getUsername())
            .eq(UserEntity::getPassword, SaSecureUtil.aesEncrypt(authProperties.getAesKey(), param.getPassword()))
            .oneOpt()
            .orElseThrow(() -> {
                BadRequestException badRequestException = new BadRequestException("登录失败");
                badRequestException.message("登录失败，账号或密码错误");
                return badRequestException;
            });
        StpUtil.login(userEntity.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterParam param) {
        if (isDebug) {
            log.debug("用户注册参数：{}", param);
        }
        try {
            UserEntity userEntity = modelMapper.map(param, UserEntity.class);
            String encryptPassword = SaSecureUtil.aesEncrypt(authProperties.getAesKey(), userEntity.getPassword());
            userEntity.setPassword(encryptPassword);
            userEntity.setAvatarImageUrl(defaultProperties.getAvatarImageUrl());
            userMapper.insert(userEntity);
            userRoleMapper.insert(new UserRoleEntity(userEntity.getId(), Role.CONSUMER.getId()));
        } catch (Exception e) {
            InternalServerException internalServerException = new InternalServerException("注册失败", e);
            internalServerException.message("用户注册失败");
            throw internalServerException;
        }
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }

    @Override
    public void check() {
        StpUtil.checkLogin();
    }

    @Override
    @Cacheable(cacheNames = "user", key = "T(cn.dev33.satoken.stp.StpUtil).getLoginIdAsString()")
    public UserVO userInfo() {
        String userId = StpUtil.getLoginIdAsString();
        return modelMapper.map(userMapper.selectById(userId), UserVO.class);
    }

}
