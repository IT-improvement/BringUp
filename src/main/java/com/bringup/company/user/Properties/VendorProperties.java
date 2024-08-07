package com.bringup.company.user.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "vendor")
public class VendorProperties {

    private String validURL; // 외부 API의 엔드포인트 URL
    private String serviceKey; // 서비스 키 또는 API 토큰

    public String getValidURL() {
        return validURL;
    }

    public void setValidURL(String validURL) {
        this.validURL = validURL;
    }

    public String getServiceKey() {
        return serviceKey;
    }

    public void setServiceKey(String serviceKey) {
        this.serviceKey = serviceKey;
    }
}