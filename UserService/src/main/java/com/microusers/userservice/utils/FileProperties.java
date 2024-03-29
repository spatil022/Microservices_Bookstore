package com.microusers.userservice.utils;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
public class FileProperties {
    private String uploadDir;
    private String jwtSecret;
    private int jwtExpirationMs;
    private int verificationMs;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public String getJwtSecret() {
        return jwtSecret;
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public int getJwtExpirationMs() {
        return jwtExpirationMs;
    }

    public void setJwtExpirationMs(int jwtExpirationMs) {
        this.jwtExpirationMs = jwtExpirationMs;
    }

    public int getVerificationMs() {
        return verificationMs;
    }

    public void setVerificationMs(int verificationMs) {
        this.verificationMs = verificationMs;
    }

}
