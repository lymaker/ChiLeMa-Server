package icu.agony.clm.config.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

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

    /**
     * <pre>
     * 验证码的持续时长（单位：秒）
     * 超过此时长验证码将会失效
     * </pre>
     */
    @DurationUnit(ChronoUnit.SECONDS)
    private Duration captchaDuration = Duration.ofSeconds(60);

}
