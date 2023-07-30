package com.halil.HumanResourcesPlatform.Authentication.dtos;

import jakarta.validation.constraints.NotEmpty;

public record LinkedinSignInDto(
        @NotEmpty
        String code,
        @NotEmpty
        String profile_url,
        String state) {
}

