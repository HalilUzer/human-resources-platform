package com.halil.HumanResourcesPlatform.Authentication.dtos;

import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record LinkedinBuildProfileDto(

        UUID candidate_id,
        @NotEmpty
        String profile_url
) {
}
