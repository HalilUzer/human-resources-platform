package com.halil.HumanResourcesPlatform.Jobs.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "personal_skills")
public class PersonalSkill {

    @Id
    @GeneratedValue
    @JsonIgnore
    private UUID personalSkillId;

    @ManyToOne
    @JoinColumn(name = "job_id")
    @JsonIgnore
    private Job job;

    private String personalSkill;

    public PersonalSkill() {}

    public UUID getPersonalSkillId() {
        return personalSkillId;
    }
    public void setPersonalSkillId(UUID personalSkillId) {
        this.personalSkillId = personalSkillId;
    }
    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
    public String getPersonalSkill() {
        return personalSkill;
    }

    public void setPersonalSkill(String personalSkill) {
        this.personalSkill = personalSkill;
    }

    public PersonalSkill(String personalSkill){
        this.personalSkill = personalSkill;
    }

}
