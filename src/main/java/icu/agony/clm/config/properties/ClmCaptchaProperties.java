package icu.agony.clm.config.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Positive;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "clm.captcha")
@Validated
public class ClmCaptchaProperties {

    /**
     * 验证码位数
     */
    @Positive
    private Integer length = 4;

    /**
     * 匹配大小写
     */
    private Boolean strict = false;

    /**
     * <pre>
     * 验证码的持续时长（单位：秒）
     * 超过此时长验证码将会失效
     * </pre>
     */
    @DurationUnit(ChronoUnit.SECONDS)
    private Duration duration = Duration.ofSeconds(60);

}
