package icu.agony.clm.controller.user.param;

import icu.agony.clm.verify.user.NicknameVerify;
import icu.agony.clm.verify.user.PasswordVerify;
import icu.agony.clm.verify.user.PhoneVerify;
import icu.agony.clm.verify.user.UsernameVerify;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class UserCreateParam {

    @NicknameVerify
    @NotBlank
    private String nickname;

    @UsernameVerify
    @NotBlank
    private String username;

    @PasswordVerify
    @NotBlank
    private String password;

    @PhoneVerify
    @NotBlank
    private String phone;

    @NotBlank
    @Email
    private String email;

}
