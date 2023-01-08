package icu.agony.clm.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import icu.agony.clm.config.properties.ClmTencentCosProperties;
import icu.agony.clm.config.properties.ClmTencentProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
@EnableConfigurationProperties({ClmTencentProperties.class, ClmTencentCosProperties.class})
@Slf4j
public class TencentConfiguration {

    @Bean
    COSCredentials cosCredentials(ClmTencentProperties tencentProperties) {
        return new BasicCOSCredentials(tencentProperties.getAccessKey(), tencentProperties.getSecretKey());
    }

    @Bean
    ClientConfig cosClientConfig(ClmTencentCosProperties cosProperties) {
        Region region = new Region(cosProperties.getRegion());
        return new ClientConfig(region);
    }

    @Bean
    COSClient cosClient(COSCredentials cosCredentials, ClientConfig clientConfig) {
        return new COSClient(cosCredentials, clientConfig);
    }

}
