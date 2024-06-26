package com.halil.HumanResourcesPlatform.Jobs.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "technical_skills")
public class TechnicalSkill {

    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.CHAR)
    @JsonIgnore
    private UUID technicalSkillId;

    private String technicalSkill;

    @ManyToOne
    @JoinColumn(name = "job_id")
    @JsonIgnore
    private Job job;

    public TechnicalSkill() {

    }

    public UUID getTechnicalSkillId() {
        return technicalSkillId;
    }

    public void setTechnicalSkillId(UUID technicalSkillId) {
        this.technicalSkillId = technicalSkillId;
    }

    public String getTechnicalSkill() {
        return technicalSkill;
    }

    public void setTechnicalSkill(String technicalSkill) {
        this.technicalSkill = technicalSkill;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public TechnicalSkill(String technicalSkill){
        this.technicalSkill = technicalSkill;
    }
}
