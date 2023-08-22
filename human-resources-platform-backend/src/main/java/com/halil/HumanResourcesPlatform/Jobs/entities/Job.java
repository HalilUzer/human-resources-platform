package com.halil.HumanResourcesPlatform.Jobs.entities;


import com.halil.HumanResourcesPlatform.Applications.entities.Application;
import com.halil.HumanResourcesPlatform.HrSpecialists.entities.HrSpecialist;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "jobs")
public class Job {
    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @GeneratedValue
    private UUID jobId;
    @ManyToOne
    @JoinColumn(name = "hr_specialist_id", nullable = false)
    HrSpecialist poster;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "job")
    private List<TechnicalSkill> technicalSkills = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "job")
    private List<PersonalSkill> personalSkills = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "job")
    private List<Application>  applications;


    private boolean isPermanent;
    private String title;
    private LocalDateTime until;


    private Status status;
    @Column(columnDefinition = "LONGTEXT")
    private String jobDescription;

    public Job(HrSpecialist poster, List<TechnicalSkill> technicalSkills, List<PersonalSkill> personalSkills,
               String title, LocalDateTime until, Status status, String jobDescription, boolean isPermanent) {
        this.poster = poster;
        this.technicalSkills = technicalSkills;
        this.personalSkills = personalSkills;
        this.title = title;
        this.until = until;
        this.status = status;
        this.jobDescription = jobDescription;
        this.isPermanent = isPermanent;
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

    public void addTechnicalSkills(TechnicalSkill technicalSkill) {
        this.technicalSkills.add(technicalSkill);
    }

    public void addPersonalSkill(PersonalSkill personalSkill) {
        this.personalSkills.add(personalSkill);
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
    public LocalDateTime getUntil() {
        return until;
    }
    public void setUntil(LocalDateTime set) {
        this.until = set;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public String getJobDescription() {
        return jobDescription;
    }
    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public void pushApplication(Application application){
        this.applications.add(application);
    }

    public void setPermanent(boolean permanent) {
        isPermanent = permanent;
    }

    public boolean isPermanent() {
        return isPermanent;
    }


    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
}
