package icu.agony.clm.controller.user;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import icu.agony.clm.consts.Role;
import icu.agony.clm.controller.user.param.UserCreateParam;
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
    void checkUsername(@RequestParam @UsernameVerify String username) {
        userService.checkUsername(username);
    }

    @PutMapping("/update")
    @SaCheckLogin
    void update(@RequestBody @Validated UserUpdateParam param) {
        userService.updateField(param);
    }

    @GetMapping("/select")
    @SaCheckLogin
    UserVO select() {
        return modelMapper.map(userService.select(), UserVO.class);
    }

    @GetMapping("/select-by-id")
    @SaCheckRole(Role.PROVIDER_NAME)
    UserVO selectById(@RequestParam @NotBlank String id) {
        return modelMapper.map(userService.selectById(id), UserVO.class);
    }

    @PostMapping("/create")
    void create(@RequestBody @Validated UserCreateParam param) {
        userService.create(param);
    }

}
