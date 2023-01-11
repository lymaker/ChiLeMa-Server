package icu.agony.clm.controller.role;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import icu.agony.clm.consts.Role;
import icu.agony.clm.controller.role.param.UserRoleCreateParam;
import icu.agony.clm.controller.role.param.UserRoleDeleteParam;
import icu.agony.clm.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-role")
@SaCheckRole(Role.MANAGER_NAME)
@RequiredArgsConstructor
public class UserRoleController {

    private final UserRoleService userRoleService;

    @PostMapping("/create")
    void create(@RequestBody @Validated UserRoleCreateParam param) {
        userRoleService.create(param.getUserId(), param.getRoleId());
    }

    @DeleteMapping("/delete")
    void delete(@RequestBody @Validated UserRoleDeleteParam param) {
        userRoleService.delete(param.getUserId(), param.getRoleId());
    }

    @GetMapping("/select")
    List<String> select() {
        return userRoleService.select(StpUtil.getLoginIdAsString());
    }

}
