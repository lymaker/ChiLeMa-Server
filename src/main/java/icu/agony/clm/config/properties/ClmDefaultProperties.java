package icu.agony.clm.config.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "clm.default")
@Validated
public class ClmDefaultProperties {

    /**
     * 默认头像地址，目前用于用户注册后的默认头像
     */
    @NotBlank
    @URL
    private String avatarImageUrl = "https://default-1300725964.cos.ap-guangzhou.myqcloud.com/avatar.png";

}
