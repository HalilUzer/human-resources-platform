package com.halil.HumanResourcesPlatform.HrSpecialist.entities;

import jakarta.persistence.*;

@Entity
public class HrSpecialist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String linkedinId;
    private String name;
    private String surname;

    private String headline;



    public HrSpecialist(int id, String linkedinId, String name, String surname, String headline) {
        this.id = id;
        this.linkedinId = linkedinId;
        this.name = name;
        this.surname = surname;
        this.headline = headline;
    }

    public HrSpecialist(){}

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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