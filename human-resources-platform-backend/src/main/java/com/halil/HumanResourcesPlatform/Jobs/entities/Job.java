package com.halil.HumanResourcesPlatform.Jobs.entities;


import com.halil.HumanResourcesPlatform.Applications.entities.Application;
import com.halil.HumanResourcesPlatform.HrSpecialists.entities.HrSpecialist;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "jobs")
public class Job {
    @Id
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

    private String title;
    private Date until;
    private Status status;
    @Column(columnDefinition = "LONGTEXT")
    private String jobDescription;

    public Job(HrSpecialist poster, List<TechnicalSkill> technicalSkills, List<PersonalSkill> personalSkills, String title, Date until, Status status, String jobDescription) {
        this.poster = poster;
        this.technicalSkills = technicalSkills;
        this.personalSkills = personalSkills;
        this.title = title;
        this.until = until;
        this.status = status;
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

    public void addTechnicalSkills(TechnicalSkill technicalSkill) {
        technicalSkill.setJob(this);
        this.technicalSkills.add(technicalSkill);
    }

    public void addPersonalSkill(PersonalSkill personalSkill) {
        personalSkill.setJob(this);
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
    public Date getUntil() {
        return until;
    }
    public void setUntil(Date set) {
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
        application.setJob(this);
    }


    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
}
