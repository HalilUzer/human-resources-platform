package com.halil.HumanResourcesPlatform.Job.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class PersonalSkill {

    @Id
    @GeneratedValue
    private UUID personalSkillId;

    @ManyToOne
    @JoinColumn(name = "job_id")
    @JsonIgnore
    private Job job;

    private String personalSkill;
}
