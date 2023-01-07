package icu.agony.clm.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import icu.agony.clm.controller.user.param.UserUpdateParam;
import icu.agony.clm.entity.UserEntity;
import icu.agony.clm.exception.BadRequestException;
import icu.agony.clm.mapper.UserMapper;
import icu.agony.clm.service.UserService;
import icu.agony.clm.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final boolean isDebug = log.isDebugEnabled();

    private final UserMapper userMapper;

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
    @CacheEvict(cacheNames = "user", key = "T(cn.dev33.satoken.stp.StpUtil).getLoginIdAsString()")
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

}
