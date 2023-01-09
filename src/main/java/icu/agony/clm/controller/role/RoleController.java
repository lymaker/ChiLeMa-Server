package icu.agony.clm.controller.role;

import cn.dev33.satoken.annotation.SaCheckRole;
import icu.agony.clm.consts.Role;
import icu.agony.clm.controller.role.vo.RoleVO;
import icu.agony.clm.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/role")
@SaCheckRole(Role.MANAGER_NAME)
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    private final ModelMapper modelMapper;

    @GetMapping("/select-all")
    List<RoleVO> selectAll() {
        /*
            随笔：此处多此一举的转换是因为RoleVO来控制返回数据，过滤敏感数据
                 后续如果实体类变动，返回的数据还是不变的，除非属性重命名
         */
        return roleService.selectAll()
            .stream()
            .map(role -> modelMapper.map(role, RoleVO.class))
            .collect(Collectors.toList());
    }

}
