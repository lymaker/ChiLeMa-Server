package icu.agony.clm.controller.user.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserVO {

    private String id;

    private String nickname;

    private String username;

    private String email;

    private String phone;

    private String avatarImageUrl;

    private Date registerTime;

}
