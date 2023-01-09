package icu.agony.clm.controller.user.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserVO {

    private String id;

    private String nickname;

    private String username;

    private String email;

    private String phone;

    private String avatarImageUrl;

    private Double money;

    private Long registerTime;

}
