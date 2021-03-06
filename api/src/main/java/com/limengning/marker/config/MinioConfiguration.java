package com.limengning.marker.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioConfiguration {

    private String url;

    private String accessKey;

    private String secretKey;

    private String bucketName;
}
