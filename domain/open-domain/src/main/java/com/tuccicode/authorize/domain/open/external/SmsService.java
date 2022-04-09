package com.tuccicode.authorize.domain.open.external;

import com.tuccicode.authorize.domain.open.config.properties.SmsProperties;

/**
 * 封装sms服务提供商的sdk，尽量做到大部分平台兼容。避免以后更换服务提供商，大批量修改代码
 *
 * @author tucci.lee
 */
public interface SmsService {

    /**
     * 获取配置属性
     *
     * @return properties
     */
    SmsProperties properties();

    /**
     * 发送短信验证码
     *
     * @param phone    手机号
     * @param signName 签名
     * @param template 模板id
     * @param param    参数
     */
    void send(String phone, String signName, String template, Object param);
}
