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
@ConfigurationProperties(prefix = "clm.tencent")
@Validated
public class ClmTencentProperties {

    /**
     * Tencent appid
     */
    @NotBlank
    private String appid;

    /**
     * Tencent secretId
     */
    @NotBlank
    private String accessKey;

    /**
     * Tencent secretKey
     */
    @NotBlank
    private String secretKey;

}
