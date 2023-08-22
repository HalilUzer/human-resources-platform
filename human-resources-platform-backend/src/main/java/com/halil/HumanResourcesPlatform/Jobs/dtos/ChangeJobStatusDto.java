package com.halil.HumanResourcesPlatform.Jobs.dtos;

import com.halil.HumanResourcesPlatform.Jobs.entities.Status;
import jakarta.validation.constraints.NotNull;

public record ChangeJobStatusDto(
        @NotNull
        String until,
        @NotNull
        Status status,
        
        @NotNull
        boolean is_permanent
) {
}
