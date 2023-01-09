package icu.agony.clm.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import icu.agony.clm.config.properties.ClmAuthProperties;
import icu.agony.clm.consts.Role;
import icu.agony.clm.controller.auth.param.LoginParam;
import icu.agony.clm.controller.user.param.UserSelectParam;
import icu.agony.clm.entity.UserEntity;
import icu.agony.clm.exception.BadRequestException;
import icu.agony.clm.exception.InternalServerException;
import icu.agony.clm.service.AuthService;
import icu.agony.clm.service.UserRoleService;
import icu.agony.clm.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final boolean isDebug = log.isDebugEnabled();

    private final UserService userService;

    private final UserRoleService userRoleService;

    private final ClmAuthProperties authProperties;

    @Override
    public void login(LoginParam param) {
        if (isDebug) {
            log.debug("用户登录参数：{}", param);
        }
        try {
            UserSelectParam userSelectParam = new UserSelectParam();
            userSelectParam.setUsername(param.getUsername());
            userSelectParam.setPassword(SaSecureUtil.aesEncrypt(authProperties.getAesKey(), param.getPassword()));
            UserEntity userEntity = userService.selectByExample(userSelectParam);
            List<String> roleList = userRoleService.select(userEntity.getId());
            if (!roleList.contains(param.getRole())) {
                BadRequestException badRequestException = new BadRequestException("未拥有此" + Role.PROVIDER_NAME + "角色，拒绝登录");
                badRequestException.message("拒绝登录");
                throw badRequestException;
            }
            StpUtil.login(userEntity.getId());
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException("服务器内部错误", e);
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

}
