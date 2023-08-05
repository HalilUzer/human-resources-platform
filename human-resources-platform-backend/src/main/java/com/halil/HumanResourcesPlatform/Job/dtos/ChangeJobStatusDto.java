package com.halil.HumanResourcesPlatform.Job.dtos;

import com.halil.HumanResourcesPlatform.Job.entities.Status;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record ChangeJobStatusDto(
        @NotNull
        Date until,
        @NotNull
        Status status
) {
}
