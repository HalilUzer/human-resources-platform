package com.halil.HumanResourcesPlatform.Applications.dtos;

import com.halil.HumanResourcesPlatform.Applications.entities.ApplicationStatus;
import jakarta.validation.constraints.NotNull;

public record ChangeApplicationStatusDto(
        @NotNull
        ApplicationStatus status
) {
}
