package icu.agony.clm.controller.auth;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import icu.agony.clm.consts.CaptchaType;
import icu.agony.clm.controller.auth.param.LoginParam;
import icu.agony.clm.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    void login(@RequestBody @Validated LoginParam param) {
        StpUtil.checkSafe(CaptchaType.LOGIN.getNickname());
        authService.login(param);
        StpUtil.closeSafe(CaptchaType.LOGIN.getNickname());
    }

    @DeleteMapping("/logout")
    @SaCheckLogin
    void logout() {
        authService.logout();
    }

    @GetMapping("/check")
    void check() {
        authService.check();
    }

}
