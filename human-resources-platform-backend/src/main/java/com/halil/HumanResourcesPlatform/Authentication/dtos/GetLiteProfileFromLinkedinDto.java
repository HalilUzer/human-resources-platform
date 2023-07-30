package com.halil.HumanResourcesPlatform.Authentication.dtos;

import jakarta.validation.constraints.NotEmpty;

public record GetLiteProfileFromLinkedinDto(
        @NotEmpty
        String localizedFirstName,
        @NotEmpty
        String localizedLastName,
        @NotEmpty
        String id
) {
}

record ProfilePicture(
        @NotEmpty
        String displayImage
) {
}