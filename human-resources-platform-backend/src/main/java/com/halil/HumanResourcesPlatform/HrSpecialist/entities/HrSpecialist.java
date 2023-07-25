package com.halil.HumanResourcesPlatform.HrSpecialist.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class HrSpecialist {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String username;

    public HrSpecialist(UUID id, String username) {
        this.id = id;
        this.username = username;
    }

    public HrSpecialist(){}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}