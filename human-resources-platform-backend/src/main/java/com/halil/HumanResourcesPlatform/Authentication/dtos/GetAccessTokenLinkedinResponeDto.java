package com.halil.HumanResourcesPlatform.Authentication.dtos;

import jakarta.validation.constraints.NotEmpty;

public record GetAccessTokenLinkedinResponeDto
    (
            @NotEmpty
            String access_token,
    @NotEmpty
    int expires_in,
    @NotEmpty
    String refresh_token,
    @NotEmpty
    int refresh_token_expires_in,
    @NotEmpty
    String scope)
{}
