package icu.agony.clm.controller.auth.param;

import icu.agony.clm.consts.Role;
import icu.agony.clm.verify.user.PasswordVerify;
import icu.agony.clm.verify.user.UsernameVerify;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class LoginParam {

    @UsernameVerify
    @NotBlank
    private String username;

    @PasswordVerify
    @NotBlank
    private String password;

    private String role = Role.CONSUMER_NAME;

}
