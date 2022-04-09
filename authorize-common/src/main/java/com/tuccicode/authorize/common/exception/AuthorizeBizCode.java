package com.tuccicode.authorize.common.exception;

import com.tuccicode.raccoon.exception.BizCode;

/**
 * @author tucci.lee
 */
public enum AuthorizeBizCode implements BizCode {
    /**
     * -1 服务器错误
     */
    SERVER_ERROR(-1, "服务器错误"),

    /**
     * 101xx 请求错误
     * 10100 参数错误
     * 10101 请求方法不支持
     * 10102 不支持的媒体类型
     * 10103 参数类型错误
     * 10104 json解析错误
     * 10105 文件大小超过限制
     * 10198 未找到
     * 10199 请求频繁
     */
    PARAMETER_ERROR(10100, "参数错误"),
    METHOD_NOT_ALLOWED(10101, "请求方法不支持"),
    UNSUPPORTED_MEDIA_TYPE(10102, "不支持的媒体类型"),
    PARAMETER_TYPE_ERROR(10103, "参数类型错误"),
    JSON_PARSE_ERROR(10104, "json解析错误"),
    FILE_SIZE_LIMIT(10105, "文件大小超过限制"),
    NOT_FOUND(10198, "未找到"),
    FREQUENT_REQUESTS(10199, "请求频繁"),

    /**
     * 102xx 权限错误
     * 10201 未登录
     * 10202 权限不足
     */
    UNAUTHENTICATED(10201, "未认证"),
    UNAUTHORIZED(10302, "未授权"),
    SIGNIN_DENIED(10303,"拒绝登录"),
    ACCOUNT_LOCKED(10304, "账号锁定"),
    USERNAME_OR_PASSWORD_ERROR(10305, "用户名或密码错误"),


    /**
     * 103xx 验证码错误
     * 10300 验证码类型错误
     * 10301 图片验证码错误
     * 10302 短信验证码错误
     * 10303 邮箱验证码错误
     */
    CAPTCHA_TYPE_NOT_SUPPORTED(10300, "验证码类型不支持"),
    IMAGE_CAPTCHA_ERROR(10301, "验证码错误"),
    SMS_CAPTCHA_ERROR(10302, "验证码错误"),
    EMAIL_CAPTCHA_ERROR(10303, "验证码错误"),

    /**
     * 20xxx 业务错误
     */
    USER_EXIST(20103, "用户已经存在"),
    USER_DONT_EXIST(20104, "用户不存在"),
    PHONE_EXIST(20105, "手机号已经存在"),
    PHONE_DONT_EXIST(20106, "手机号不存在"),
    EMAIL_EXIST(20107, "邮箱已经存在"),
    EMAIL_DONT_EXIST(20108, "邮箱不存在"),
    PHONE_REQUIRED(20109, "手机号不能为空"),
    PASSWORD_REQUIRED(20110, "密码不能为空"),
    EMAIL_REQUIRED(20111, "邮箱不能为空"),
    TOKEN_VERIFY_ERROR(20112, "token验证失败"),
    ;

    int code;
    String message;

    AuthorizeBizCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
