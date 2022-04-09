package com.tuccicode.authorize.domain.open.external.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.StringUtils;
import com.aliyun.oss.model.PutObjectResult;
import com.tuccicode.authorize.domain.open.config.properties.AliyunProperties;
import com.tuccicode.authorize.domain.open.config.properties.OssProperties;
import com.tuccicode.authorize.domain.open.external.FsService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * 阿里云 OSS 存储服务
 *
 * @author tucci.lee
 */
@Service
@EnableConfigurationProperties({AliyunProperties.class})
public class AliyunOssService implements FsService {

    private final AliyunProperties aliyunProperties;

    public AliyunOssService(AliyunProperties aliyunProperties) {
        this.aliyunProperties = aliyunProperties;
    }

    protected OSS client() {
        return new OSSClientBuilder().build(aliyunProperties.getOss().getEndpoint(),
                aliyunProperties.getAccessKey(), aliyunProperties.getAccessSecret());
    }

    protected String getBucket() {
        return aliyunProperties.getOss().getBucketName();
    }

    @Override
    public String getDomain() {
        OssProperties oss = aliyunProperties.getOss();
        String domain = oss.getDomain();
        if (StringUtils.isNullOrEmpty(domain)) {
            domain = oss.getBucketName() + "." + oss.getEndpoint() + "/";
        }
        return domain;
    }

    @Override
    public void upload(String key, InputStream is) {
        OSS oss = client();
        PutObjectResult result = oss.putObject(getBucket(), key, is);
        oss.shutdown();
    }

    @Override
    public void delete(String key) {
        OSS oss = client();
        oss.deleteObject(getBucket(), key);
        oss.shutdown();
    }

}
