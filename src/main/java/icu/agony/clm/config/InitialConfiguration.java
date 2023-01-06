package icu.agony.clm.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.CreateBucketRequest;
import icu.agony.clm.config.properties.ClmTencentCosProperties;
import icu.agony.clm.config.properties.ClmTencentProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringBootConfiguration;

import javax.annotation.PostConstruct;

@SpringBootConfiguration
@RequiredArgsConstructor
@Slf4j
public class InitialConfiguration {

    private final ClmTencentProperties tencentProperties;

    private final ClmTencentCosProperties tencentCosProperties;

    private final COSClient client;

    @PostConstruct
    void createBucket() {
        String bucketName = String.format("%s-%s", tencentCosProperties.getBucketName(), tencentProperties.getAppid());
        boolean bucketExist = client.doesBucketExist(bucketName);
        if (!bucketExist) {
            log.info("创建存储桶 {}", bucketName);
            CreateBucketRequest bucketRequest = new CreateBucketRequest(bucketName);
            bucketRequest.setCannedAcl(CannedAccessControlList.PublicRead);
            client.createBucket(bucketName);
        }
    }

}
