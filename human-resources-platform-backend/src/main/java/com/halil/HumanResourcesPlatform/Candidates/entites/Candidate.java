package com.halil.HumanResourcesPlatform.Candidates.entites;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String linkedinId;
    private String name;
    private String surname;

    private String headline;



    public Candidate(UUID id, String linkedinId, String name, String surname, String headline) {
        this.id = id;
        this.linkedinId = linkedinId;
        this.name = name;
        this.surname = surname;
        this.headline = headline;
    }

    public Candidate(){}

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLinkedinId() {
        return linkedinId;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setLinkedinId(String linkedinId) {
        this.linkedinId = linkedinId;
    }
}