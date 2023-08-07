package com.halil.HumanResourcesPlatform.Jobs.dtos;

import com.halil.HumanResourcesPlatform.Jobs.entities.Status;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record ChangeJobStatusDto(
        @NotNull
        Date until,
        @NotNull
        Status status
) {
}
