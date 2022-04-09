package com.tuccicode.authorize.domain.open.external.impl;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.teaopenapi.models.Config;
import com.tuccicode.authorize.domain.open.config.properties.AliyunProperties;
import com.tuccicode.authorize.domain.open.config.properties.SmsProperties;
import com.tuccicode.authorize.domain.open.external.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * 阿里云短信服务
 *
 * @author tucci.lee
 */
@Service
@EnableConfigurationProperties({AliyunProperties.class})
public class AliyunSmsService implements SmsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AliyunProperties aliyunProperties;

    public AliyunSmsService(AliyunProperties aliyunProperties) {
        this.aliyunProperties = aliyunProperties;
    }

    protected Client client() throws Exception {
        Config config = new Config()
                .setAccessKeyId(aliyunProperties.getAccessKey())
                .setAccessKeySecret(aliyunProperties.getAccessSecret())
                .setEndpoint("dysmsapi.aliyuncs.com");
        return new Client(config);
    }

    @Override
    public SmsProperties properties() {
        return this.aliyunProperties.getSms();
    }

    @Override
    public void send(String phone, String signName, String template, Object param) {
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(phone)
                .setSignName(signName)
                .setTemplateCode(template);
        if (param instanceof String) {
            sendSmsRequest.setTemplateParam((String) param);
        }
        try {
            this.client().sendSms(sendSmsRequest);
            logger.info("send sms success {}, template:{}", phone, template);
        } catch (Exception e) {
            logger.error("send sms error {}", phone, e);
        }
    }
}
