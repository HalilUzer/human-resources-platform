package com.halil.HumanResourcesPlatform.Authentication.security;

public record JwtPayload(
        String iss,
        String exp,
        String iat,
        String role
) {
}
