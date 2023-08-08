package com.halil.HumanResourcesPlatform.HrSpecialists.entities;

import com.halil.HumanResourcesPlatform.Jobs.entities.Job;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class HrSpecialist {

    @Id
    @GeneratedValue
    private UUID hrSpecialistId;

    @Column(unique = true)
    private String username;

    @OneToMany(mappedBy = "poster")
    private List<Job> postedJobs;


    public HrSpecialist(UUID hrSpecialistId, String username) {
        this.hrSpecialistId = hrSpecialistId;
        this.username = username;
    }

    public HrSpecialist(){}

    public UUID getHrSpecialistId() {
        return hrSpecialistId;
    }

    public void setHrSpecialistId(UUID id) {
        this.hrSpecialistId = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Job> getPostedJobs() {
        return postedJobs;
    }


    public void pushJob(Job job){
        this.postedJobs.add(job);
    }

    public void setPostedJobs(List<Job> postedJobs) {
        this.postedJobs = postedJobs;
    }
}