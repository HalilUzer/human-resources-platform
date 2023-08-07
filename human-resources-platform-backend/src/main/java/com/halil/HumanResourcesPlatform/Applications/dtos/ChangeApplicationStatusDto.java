package com.halil.HumanResourcesPlatform.Applications.dtos;

import com.halil.HumanResourcesPlatform.Applications.entities.ApplicationStatus;
import jakarta.validation.constraints.NotEmpty;

public record ChangeApplicationStatusDto(
        @NotEmpty
        ApplicationStatus status
) {
}
