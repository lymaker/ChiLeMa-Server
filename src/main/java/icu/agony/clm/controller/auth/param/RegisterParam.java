package icu.agony.clm.controller.auth.param;

import icu.agony.clm.verify.user.NicknameVerify;
import icu.agony.clm.verify.user.PasswordVerify;
import icu.agony.clm.verify.user.PhoneVerify;
import icu.agony.clm.verify.user.UsernameVerify;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class RegisterParam {

    @NicknameVerify
    private String nickname;

    @UsernameVerify
    private String username;

    @PasswordVerify
    private String password;

    @PhoneVerify
    private String phone;

    @NotNull
    @Email
    private String email;

}
