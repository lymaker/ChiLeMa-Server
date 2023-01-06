package icu.agony.clm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "user_role", keepGlobalPrefix = true)
public class UserRoleEntity {

    private String userId;

    private Integer roleId;

}
