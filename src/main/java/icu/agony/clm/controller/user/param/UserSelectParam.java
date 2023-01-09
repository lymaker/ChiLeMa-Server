package icu.agony.clm.controller.user.param;

import icu.agony.clm.verify.user.NicknameVerify;
import icu.agony.clm.verify.user.PasswordVerify;
import icu.agony.clm.verify.user.PhoneVerify;
import icu.agony.clm.verify.user.UsernameVerify;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;

@Getter
@Setter
@ToString
public class UserSelectParam {

    private String id;

    @NicknameVerify
    private String nickname;

    @UsernameVerify
    private String username;

    @PasswordVerify
    private String password;

    @PhoneVerify
    private String phone;

    @Email
    private String email;

    private Double money;

}
