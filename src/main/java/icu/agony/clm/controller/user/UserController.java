package icu.agony.clm.controller.user;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import icu.agony.clm.annotation.CheckCaptcha;
import icu.agony.clm.consts.CaptchaType;
import icu.agony.clm.consts.Role;
import icu.agony.clm.controller.user.param.UserCreateParam;
import icu.agony.clm.controller.user.param.UserSelectParam;
import icu.agony.clm.controller.user.param.UserUpdateParam;
import icu.agony.clm.controller.user.vo.UserVO;
import icu.agony.clm.service.UserService;
import icu.agony.clm.verify.user.UsernameVerify;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    @GetMapping("/check-username")
    @Validated
    void checkUsername(@RequestParam @UsernameVerify String username) {
        userService.checkUsername(username);
    }

    @PutMapping("/update")
    @SaCheckLogin
    void update(@RequestBody @Validated UserUpdateParam param) {
        userService.update(param);
    }

    @GetMapping("/select")
    @SaCheckLogin
    UserVO select() {
        return modelMapper.map(userService.select(), UserVO.class);
    }

    @GetMapping("/select-by-id")
    @SaCheckRole(value = {Role.MANAGER_NAME, Role.PROVIDER_NAME}, mode = SaMode.OR)
    @Validated
    UserVO selectById(@RequestParam @NotBlank String id) {
        return modelMapper.map(userService.selectById(id), UserVO.class);
    }

    @GetMapping("/select-by-example")
    @SaCheckRole(value = {Role.MANAGER_NAME, Role.PROVIDER_NAME}, mode = SaMode.OR)
    UserVO selectByExample(@RequestBody @Validated UserSelectParam param) {
        return modelMapper.map(userService.selectByExample(param), UserVO.class);
    }

    @PostMapping("/create")
    @CheckCaptcha(CaptchaType.REGISTER)
    void create(@RequestBody @Validated UserCreateParam param) {
        userService.create(param);
    }

}
