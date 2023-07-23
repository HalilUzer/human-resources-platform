package com.halil.HumanResourcesPlatform.Authentication.configs;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationProperties(prefix = "linkedinoauth")
@ConfigurationPropertiesScan
public record LinkedinOauthConfigProperties (
        String clientSecret,
        String clientId,
        String redirectUri
){
}
