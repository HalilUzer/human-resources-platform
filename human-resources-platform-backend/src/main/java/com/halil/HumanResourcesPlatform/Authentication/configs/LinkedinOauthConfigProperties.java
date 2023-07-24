package com.halil.HumanResourcesPlatform.Authentication.configs;


import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "linkedinoauth")
@ConfigurationPropertiesScan
public record LinkedinOauthConfigProperties (
        @NotEmpty
        String clientSecret,
        @NotEmpty
        String clientId,
        @NotEmpty
        String redirectUri
){
}
