package com.halil.HumanResourcesPlatform.Authentication.dtos;

import jakarta.validation.constraints.NotEmpty;

public record ErrorDto(
        @NotEmpty
        String message
) {
}
