package com.halil.HumanResourcesPlatform.Job.dtos;

import com.halil.HumanResourcesPlatform.Job.entities.PersonalSkill;
import com.halil.HumanResourcesPlatform.Job.entities.State;
import com.halil.HumanResourcesPlatform.Job.entities.TechnicalSkill;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record CreateJobDto (
        @NotEmpty
        String title,
        @NotEmpty
        String job_description,
        @NotEmpty
        State state,
        @NotEmpty
        Date until,
        List<TechnicalSkill> technicalSkills,
        List<PersonalSkill> personalSkills,

        UUID poster
){}
