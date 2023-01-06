package icu.agony.clm.config.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "clm.auth")
@Validated
public class ClmAuthProperties {

    /**
     * <pre>
     * 用户密码加密的密钥
     *
     * 注意：
     * 不可随意更换，因为当密钥更换后，现有用户都将无法登录！
     * 因为加密的密钥不一致会使加密后的密文不匹配
     * 切记！
     * </pre>
     */
    @NotBlank
    private String aesKey = "$i72kT^yRkOM1pD";

}
