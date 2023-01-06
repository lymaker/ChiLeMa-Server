package icu.agony.clm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "user", keepGlobalPrefix = true)
public class UserEntity {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String nickname;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String avatarImageUrl;

    private Date registerTime;

}
