package com.halil.HumanResourcesPlatform.Authentication.dtos;


import jakarta.validation.constraints.NotEmpty;

public record LdapSignInDto(
    @NotEmpty
String username,
    @NotEmpty
    String password
){}
