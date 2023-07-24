package com.halil.HumanResourcesPlatform.Authentication.dtos;

import jakarta.validation.constraints.NotEmpty;

public record GetLiteProfileFromLinkedinDto(
        @NotEmpty
        String localizedFirstName,
        @NotEmpty
        String localizedHeadline,
        @NotEmpty
        String vanityName,
        @NotEmpty
        String id,
        @NotEmpty
        String localizedLastName

) {
}

record ProfilePicture(
        @NotEmpty
        String displaImage
) {
}