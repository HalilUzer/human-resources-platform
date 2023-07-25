package com.halil.HumanResourcesPlatform.Authentication.dtos;


import org.hibernate.validator.constraints.UUID;

public record JwtDto(
        @UUID
        String token,

        String role
) {
}
