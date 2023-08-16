package com.halil.HumanResourcesPlatform.Candidates.entites;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.halil.HumanResourcesPlatform.Applications.entities.Application;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "candidates")
public class Candidate {

    @Id
    @GeneratedValue
    private UUID candidateId;
    @Column(unique = true)
    @JsonIgnore
    private String linkedinId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidate")
    private List<Experience> experiences = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidate")
    private List<Education> educations = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidate")
    private List<Application> applications = new ArrayList<>();

    @Column(columnDefinition = "LONGTEXT")
    private String about;
    private String name;
    private String surname;
    private String headline;
    private String profileUrl;
    private String email;
    private String imageSource;


    public Candidate() {
    }

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

    public UUID getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(UUID id) {
        this.candidateId = id;
    }

    public String getLinkedinId() {
        return linkedinId;
    }


    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setLinkedinId(String linkedinId) {
        this.linkedinId = linkedinId;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }

    public String getHeadline() {
        return headline;
    }

    public void pushExperience(Experience experience) {
        this.experiences.add(experience);
        experience.setCandidate(this);
    }

    public void pushEducation(Education education) {
        this.educations.add(education);
        education.setCandidate(this);
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public List<Education> getEducations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    public void pushApplication(Application application){
        this.applications.add(application);
        application.setCandidate(this);
    }

}