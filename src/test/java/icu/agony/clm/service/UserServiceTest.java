package icu.agony.clm.service;

import cn.dev33.satoken.secure.SaSecureUtil;
import icu.agony.clm.Application;
import icu.agony.clm.config.properties.ClmAuthProperties;
import icu.agony.clm.controller.user.param.UserCreateParam;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = Application.class)
@Slf4j
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Resource
    private ClmAuthProperties authProperties;

    @Test
    void insertDefaultUser() {
        UserCreateParam param = new UserCreateParam();
        param.setNickname("Anna");
        param.setUsername("1265894132");
        param.setPassword("a12345678");
        param.setPhone("12312312312");
        param.setEmail("1265894132@qq.com");
        userService.create(param);
    }

    @ParameterizedTest
    @ValueSource(strings = "a12345678")
    void generateEncryptPassword(String password) {
        String text = SaSecureUtil.aesEncrypt(authProperties.getAesKey(), password);
        log.debug("加密后的密码: {}", text);
    }

}
