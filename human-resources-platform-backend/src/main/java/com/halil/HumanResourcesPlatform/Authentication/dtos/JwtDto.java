package com.halil.HumanResourcesPlatform.Authentication.dtos;


import java.util.UUID;

public record JwtDto(

        String token,

        String role,

        UUID candidate_id

) { }

