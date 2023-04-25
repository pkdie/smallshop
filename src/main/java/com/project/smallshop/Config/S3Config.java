package com.project.smallshop.Config;

import org.springframework.beans.factory.annotation.Value;

public class S3Config {
    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;
}
