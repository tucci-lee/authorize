package com.tuccicode.authorize.domain.open.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author tucci.lee
 */
@ConfigurationProperties("aliyun")
public class AliyunProperties {

    private String accessKey;
    private String accessSecret;
    private SmsProperties sms;
    private OssProperties oss;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }

    public SmsProperties getSms() {
        return sms;
    }

    public void setSms(SmsProperties sms) {
        this.sms = sms;
    }

    public OssProperties getOss() {
        return oss;
    }

    public void setOss(OssProperties oss) {
        this.oss = oss;
    }
}
