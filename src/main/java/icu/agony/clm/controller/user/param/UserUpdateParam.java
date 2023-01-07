package icu.agony.clm.controller.user.param;

import icu.agony.clm.verify.user.NicknameVerify;
import icu.agony.clm.verify.user.PasswordVerify;
import icu.agony.clm.verify.user.PhoneVerify;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Email;

@Getter
@Setter
@ToString
public class UserUpdateParam {

    @NicknameVerify
    private String nickname;

    @PasswordVerify
    private String password;

    @PhoneVerify
    private String phone;

    @Email
    private String email;

    @URL
    private String avatarImageUrl;

}
