package icu.agony.clm.controller.auth.param;

import icu.agony.clm.verify.user.PasswordVerify;
import icu.agony.clm.verify.user.UsernameVerify;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginParam {

    @UsernameVerify
    private String username;

    @PasswordVerify
    private String password;

    private Boolean isStore = false;

}
