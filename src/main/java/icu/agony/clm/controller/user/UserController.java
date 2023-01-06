package icu.agony.clm.controller.user;

import cn.dev33.satoken.annotation.SaCheckLogin;
import icu.agony.clm.controller.auth.param.RegisterParam;
import icu.agony.clm.exception.BadRequestException;
import icu.agony.clm.service.UserService;
import icu.agony.clm.verify.user.NicknameVerify;
import icu.agony.clm.verify.user.UsernameVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.Set;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final Validator validator;

    @GetMapping("/check-username")
    void checkUsername(@RequestParam @UsernameVerify String username) {
        userService.checkUsername(username);
    }

    @PutMapping("/update/avatar")
    @SaCheckLogin
    void updateAvatar(MultipartFile file) {
        userService.updateAvatar(file);
    }

    @PutMapping("/update/{field:nickname|password|email|phone}")
    @SaCheckLogin
    void updateUserField(@PathVariable String field, @RequestParam @NicknameVerify String value) {
        verifyUserRequestParam(field, value);
        userService.updateField(field, value);
    }

    private void verifyUserRequestParam(String field, String value) {
        Set<ConstraintViolation<RegisterParam>> result = validator.validateValue(RegisterParam.class, field, value, Default.class);
        if (result.size() > 0) {
            throw new BadRequestException("非法请求参数");
        }
    }

}
