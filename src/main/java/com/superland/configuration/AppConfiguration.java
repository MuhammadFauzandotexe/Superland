package com.superland.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
@Data
public class AppConfiguration {
    private String redirectLink;
    private String midtransServerKey;
    private String midtransApiUrl;
    private String callBackUrl;
}
