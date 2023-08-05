package com.halil.HumanResourcesPlatform.Authentication.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record LinkedinBuildProfileDto(

        @NotNull
        UUID candidate_id,
        @NotEmpty
        String profile_url
) {
}
