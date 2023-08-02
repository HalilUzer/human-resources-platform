package com.halil.HumanResourcesPlatform.Job.entities;


import com.halil.HumanResourcesPlatform.HrSpecialist.entities.HrSpecialist;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.List;

@Entity
public class Job {
    @Id
    @GeneratedValue
    private UUID jobId;
    @ManyToOne
    HrSpecialist poster;
    @OneToMany(cascade = CascadeType.ALL)
    private List<TechnicalSkill> technicalSkills = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    private List<PersonalSkill> personalSkills = new ArrayList<>();
    private String title;
    private Date until;
    private State state;
    @Column(columnDefinition = "LONGTEXT")
    private String jobDescription;

    public Job(HrSpecialist poster, List<TechnicalSkill> technicalSkills, List<PersonalSkill> personalSkills, String title, Date until, State state, String jobDescription) {
        this.poster = poster;
        this.technicalSkills = technicalSkills;
        this.personalSkills = personalSkills;
        this.title = title;
        this.until = until;
        this.state = state;
        this.jobDescription = jobDescription;
    }

    public Job() {
    }

    public UUID getJobId() {
        return jobId;
    }

    public void setJobId(UUID jobId) {
        this.jobId = jobId;
    }

    public HrSpecialist getPoster() {
        return poster;
    }

    public void setPoster(HrSpecialist poster) {
        this.poster = poster;
    }

    public List<TechnicalSkill> getTechnicalSkills() {
        return technicalSkills;
    }

    public void setTechnicalSkills(List<TechnicalSkill> technicalSkills) {
        this.technicalSkills = technicalSkills;
    }

    public List<PersonalSkill> getPersonalSkills() {
        return personalSkills;
    }

    public void setPersonalSkills(List<PersonalSkill> personalSkills) {
        this.personalSkills = personalSkills;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getUntil() {
        return until;
    }

    public void setUntil(Date set) {
        this.until = set;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }
}
