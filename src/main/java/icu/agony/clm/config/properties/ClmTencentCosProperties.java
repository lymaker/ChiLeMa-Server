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
@ConfigurationProperties(prefix = "clm.tencent.cos")
@Validated
public class ClmTencentCosProperties {

    /**
     * 存储桶地域
     */
    @NotBlank
    private String region;

    /**
     * 存储桶名称，不存在则会自动创建
     */
    @NotBlank
    private String bucketName;

}
