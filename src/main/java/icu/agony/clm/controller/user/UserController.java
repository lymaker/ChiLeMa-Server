package icu.agony.clm.controller.user;

import cn.dev33.satoken.annotation.SaCheckLogin;
import icu.agony.clm.service.UserService;
import icu.agony.clm.verify.user.UsernameVerify;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/check-username")
    void checkUsername(@RequestParam @UsernameVerify String username) {
        userService.checkUsername(username);
    }

    @PostMapping("/update/avatar")
    @SaCheckLogin
    void updateAvatar(MultipartFile file) {
        userService.updateAvatar(file);
    }

}
