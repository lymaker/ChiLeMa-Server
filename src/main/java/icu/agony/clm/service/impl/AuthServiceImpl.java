package icu.agony.clm.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import icu.agony.clm.config.properties.ClmAuthProperties;
import icu.agony.clm.controller.auth.param.LoginParam;
import icu.agony.clm.entity.UserEntity;
import icu.agony.clm.exception.BadRequestException;
import icu.agony.clm.mapper.UserMapper;
import icu.agony.clm.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final boolean isDebug = log.isDebugEnabled();

    private final UserMapper userMapper;

    private final ClmAuthProperties authProperties;

    @Override
    public void login(LoginParam param) {
        if (isDebug) {
            log.debug("用户登录参数：{}", param);
        }
        // 待办：后续可能会优化成从缓存读取
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
    public void logout() {
        StpUtil.logout();
    }

    @Override
    public void check() {
        StpUtil.checkLogin();
    }

}
