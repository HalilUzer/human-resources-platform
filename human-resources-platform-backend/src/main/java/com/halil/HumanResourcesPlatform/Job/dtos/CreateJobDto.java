package com.halil.HumanResourcesPlatform.Job.dtos;

import com.halil.HumanResourcesPlatform.Job.entities.Status;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;


public record CreateJobDto (
        @NotEmpty
        String title,
        @NotEmpty
        String job_description,

        @NotNull
        Status status,
        @NotNull
        Date until,
        List<String> technical_skills,
        List<String> personal_skills
){}
