package com.tuccicode.authorize.domain.open.config.properties;

/**
 * @author tucci.lee
 */
public class OssProperties {

    private String endpoint;
    private String bucketName;
    private String domain;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
