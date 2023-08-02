package com.halil.HumanResourcesPlatform.Job.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class TechnicalSkill {

    @Id
    @GeneratedValue
    private UUID technicalSkillId;

    private String technicalSkill;

    @ManyToOne
    @JoinColumn(name = "job_id")
    @JsonIgnore
    private Job job;


}
