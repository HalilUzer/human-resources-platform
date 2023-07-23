package com.halil.HumanResourcesPlatform.Authentication.dtos;

public record GetAccessTokenLinkedinResponeDto
    (String access_token,
    int expires_in,
    String refresh_token,
    int refresh_token_expires_in,
    String scope)
{}
