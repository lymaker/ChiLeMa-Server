package icu.agony.clm.controller.auth.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class UserInfoVO {

    private String id;

    private String nickname;

    private String username;

    private String email;

    private String phone;

    private String avatarImageUrl;

    private Double money;

    private Date registerTime;

}
