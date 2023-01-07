package icu.agony.clm.controller.user;

import cn.dev33.satoken.annotation.SaCheckLogin;
import icu.agony.clm.controller.user.param.UserUpdateParam;
import icu.agony.clm.service.UserService;
import icu.agony.clm.verify.user.UsernameVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/check-username")
    void checkUsername(@RequestParam @UsernameVerify String username) {
        userService.checkUsername(username);
    }

    @PutMapping("/update/field")
    @SaCheckLogin
    void updateField(@RequestBody @Validated UserUpdateParam param) {
        userService.updateField(param);
    }

}
