package com.czl.console.backend.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Author: CHEN ZHI LING
 * Date: 2022/7/29
 * Description:
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class SecurityProperties {
    /** Request Headers ： Authorization */
    private String header;

    /** 令牌前缀，最后留个空格 Bearer */
    private String tokenStartWith;

    /** 必须使用最少88位的Base64对该令牌进行编码 */
    private String base64Secret;

    /** 令牌过期时间 此处单位/毫秒 */
    private Long tokenValidityInSeconds;

    /** 在线用户 key，根据 key 查询 redis 中在线用户的数据 */
    private String onlineKey;

    /** 验证码 key */
    private String codeKey;

    public String getTokenStartWith() {
        return tokenStartWith + " ";
    }
    public void setTokenStartWith(String tokenStartWith) {
        this.tokenStartWith = tokenStartWith;
    }
    public String getHeader() {
        return header;
    }
    public void setHeader(String header) {
        this.header = header;
    }
    public String getBase64Secret() {
        return base64Secret;
    }
    public void setBase64Secret(String base64Secret) {
        this.base64Secret = base64Secret;
    }
    public Long getTokenValidityInSeconds() {
        return tokenValidityInSeconds;
    }
    public void setTokenValidityInSeconds(Long tokenValidityInSeconds) {
        this.tokenValidityInSeconds = tokenValidityInSeconds;
    }
    public String getOnlineKey() {
        return onlineKey;
    }
    public void setOnlineKey(String onlineKey) {
        this.onlineKey = onlineKey;
    }
    public String getCodeKey() {
        return codeKey;
    }
    public void setCodeKey(String codeKey) {
        this.codeKey = codeKey;
    }
}
