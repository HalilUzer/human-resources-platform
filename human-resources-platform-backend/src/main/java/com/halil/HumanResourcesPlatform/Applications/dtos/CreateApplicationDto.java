package com.halil.HumanResourcesPlatform.Applications.dtos;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateApplicationDto (
        @NotNull
        UUID job_id
){ }

