package icu.agony.clm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "role", keepGlobalPrefix = true)
public class RoleEntity {

    @TableId(type = IdType.AUTO)
    private String id;

    private String nickname;

}
