package icu.agony.clm.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import icu.agony.clm.config.properties.ClmAuthProperties;
import icu.agony.clm.entity.UserEntity;
import icu.agony.clm.exception.BadRequestException;
import icu.agony.clm.mapper.UserMapper;
import icu.agony.clm.service.UserService;
import icu.agony.clm.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final boolean isDebug = log.isDebugEnabled();

    private final UserMapper userMapper;

    private final ModelMapper modelMapper;

    private final COSClient cosClient;

    @Value("#{'${clm.tencent.cos.bucket-name}' + '-' + '${clm.tencent.appid}'}")
    private String bucketName;

    private final ClmAuthProperties authProperties;

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
    public void updateAvatar(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            // 上传头像
            String fileName = Objects.requireNonNull(file.getOriginalFilename());
            String fileType = FileUtil.type(fileName);
            String bucketKey = String.format("%s/%s", StpUtil.getLoginId(), "avatar");
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/" + fileType);
            metadata.setContentLength(inputStream.available());
            cosClient.putObject(bucketName, bucketKey, inputStream, metadata);

            // 数据库更新
            URL avatarImageUrl = cosClient.getObjectUrl(bucketName, bucketKey);
            UserEntity userEntity = new UserEntity();
            userEntity.setId(StpUtil.getLoginIdAsString());
            userEntity.setAvatarImageUrl(avatarImageUrl.toString());
            userMapper.updateById(userEntity);
        } catch (IOException | NullPointerException e) {
            BadRequestException badRequestException = new BadRequestException("用户头像上传失败", e);
            badRequestException.message("更新信息失败");
            throw badRequestException;
        }
    }

    @Override
    @CacheEvict(cacheNames = "user", key = "T(cn.dev33.satoken.stp.StpUtil).getLoginIdAsString()")
    public void updateField(String field, String value) {
        try {
            if (isDebug) {
                log.debug("更新用户 {} 字段为 {}", field, value);
            }

            UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", StpUtil.getLoginIdAsString());

            // 如果修改的是字段为password，则进行字段加密
            if ("password".equals(field)) {
                value = SaSecureUtil.aesEncrypt(authProperties.getAesKey(), value);
            }

            updateWrapper.set(field, value);
            userMapper.update(null, updateWrapper);
        } catch (Exception e) {
            BadRequestException badRequestException = new BadRequestException("更新用户信息失败", e);
            badRequestException.message("更新信息失败");
            throw badRequestException;
        }
    }

}
