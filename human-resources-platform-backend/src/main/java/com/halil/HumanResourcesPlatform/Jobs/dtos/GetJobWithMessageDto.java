package com.halil.HumanResourcesPlatform.Jobs.dtos;

import com.halil.HumanResourcesPlatform.Jobs.entities.Job;

public record GetJobWithMessageDto(
        Job job,
        boolean isApplied
) {
}
