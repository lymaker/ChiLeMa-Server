package icu.agony.clm.controller.auth;

import cn.dev33.satoken.annotation.SaCheckLogin;
import icu.agony.clm.annotation.CheckCaptcha;
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
    @CheckCaptcha(CaptchaType.LOGIN)
    void login(@RequestBody @Validated LoginParam param) {
        authService.login(param);
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
