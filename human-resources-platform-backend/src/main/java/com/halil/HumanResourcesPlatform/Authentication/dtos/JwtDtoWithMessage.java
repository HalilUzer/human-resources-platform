package com.halil.HumanResourcesPlatform.Authentication.dtos;

import java.util.UUID;

public record JwtDtoWithMessage(
        String token,

        String role,

        UUID candidate_id,
        String message
) { }
