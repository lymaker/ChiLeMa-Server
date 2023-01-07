package icu.agony.clm.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import icu.agony.clm.config.properties.ClmTencentCosProperties;
import icu.agony.clm.config.properties.ClmTencentProperties;
import icu.agony.clm.controller.upload.param.UploadImageParam;
import icu.agony.clm.exception.BadRequestException;
import icu.agony.clm.service.UploadService;
import icu.agony.clm.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
@Slf4j
public class UploadServiceImpl implements UploadService {

    private final COSClient cosClient;

    private final String bucketName;

    private final ObjectMapper objectMapper;

    public UploadServiceImpl(COSClient cosClient,
                             ClmTencentProperties tencentProperties,
                             ClmTencentCosProperties tencentCosProperties,
                             ObjectMapper objectMapper) {
        this.cosClient = cosClient;
        this.bucketName = tencentCosProperties.getBucketName() + "-" + tencentProperties.getAppid();
        this.objectMapper = objectMapper;
    }

    @Override
    public String image(UploadImageParam param) {
        try (InputStream inputStream = param.getFile().getInputStream()) {
            String bucketKey = String.format("%s/image/%s", StpUtil.getLoginIdAsString(), IdUtil.uuid());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(inputStream.available());
            objectMetadata.setContentType(param.getContentType());
            cosClient.putObject(bucketName, bucketKey, inputStream, objectMetadata);
            URL imageUrl = cosClient.getObjectUrl(bucketName, bucketKey);
            ObjectNode json = objectMapper.createObjectNode();
            json.putPOJO("url", imageUrl);
            return json.toString();
        } catch (IOException e) {
            BadRequestException badRequestException = new BadRequestException("图片上传失败", e);
            badRequestException.message("图片上传失败");
            throw badRequestException;
        }
    }

}
