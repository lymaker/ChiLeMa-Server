package icu.agony.clm.controller.auth;

import cn.dev33.satoken.annotation.SaCheckLogin;
import icu.agony.clm.controller.auth.param.LoginParam;
import icu.agony.clm.controller.auth.param.RegisterParam;
import icu.agony.clm.controller.auth.vo.UserInfoVO;
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
        authService.login(param);
    }

    @PostMapping("/register")
    void register(@RequestBody @Validated RegisterParam param) {
        authService.register(param);
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

    @GetMapping("/user-info")
    @SaCheckLogin
    UserInfoVO userInfo() {
        return authService.userInfo();
    }

}
