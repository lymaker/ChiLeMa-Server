package icu.agony.clm.interceptor;

import cn.dev33.satoken.stp.StpInterface;
import icu.agony.clm.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StpInterfaceImpl implements StpInterface {

    private final UserRoleService userRoleService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return null;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return userRoleService.select((String) loginId);
    }

}
