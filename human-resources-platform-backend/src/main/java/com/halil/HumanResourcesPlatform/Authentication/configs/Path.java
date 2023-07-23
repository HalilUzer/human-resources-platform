package com.halil.HumanResourcesPlatform.Authentication.configs;

import org.springframework.http.HttpMethod;

public record Path(
        String path,
        HttpMethod method
) {
}
