package com.halil.HumanResourcesPlatform.Candidate.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.UUID;

@Entity(name = "educations")
public class Education {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonIgnore
    UUID educationId;

    String date;
    String university;
    String chapter;
    @ManyToOne
    @JoinColumn(name = "candidate_id")
    @JsonIgnore
    Candidate candidate;

    public UUID getEducationId() {
        return educationId;
    }

    public void setEducationId(UUID uuid) {
        this.educationId = uuid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
}