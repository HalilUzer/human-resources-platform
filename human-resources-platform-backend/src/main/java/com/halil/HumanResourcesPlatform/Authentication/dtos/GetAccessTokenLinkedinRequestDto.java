package com.halil.HumanResourcesPlatform.Authentication.dtos;

import jakarta.validation.constraints.NotEmpty;

public record GetAccessTokenLinkedinRequestDto(
        @NotEmpty
        String grant_type,
        @NotEmpty
        String code,
        @NotEmpty
        String client_id,
        @NotEmpty
        String client_secret,
        @NotEmpty
        String redirect_uri
) { }
