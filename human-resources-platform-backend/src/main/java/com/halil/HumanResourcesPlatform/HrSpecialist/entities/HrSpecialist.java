package com.halil.HumanResourcesPlatform.HrSpecialist.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class HrSpecialist {

    @Id
    @GeneratedValue
    private UUID hrSpecialistId;

    @Column(unique = true)
    private String username;


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
}