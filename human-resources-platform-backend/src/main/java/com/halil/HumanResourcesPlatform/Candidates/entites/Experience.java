package com.halil.HumanResourcesPlatform.Candidates.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity(name = "experiences")
public class Experience {

    @Id
    @GeneratedValue
    @JsonIgnore
    @JdbcTypeCode(SqlTypes.CHAR)
    UUID experienceId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    Candidate candidate;
    String title;
    String date;
    String company;
    @Column(columnDefinition = "LONGTEXT")
    String description;

    public UUID getExperienceId() {
        return experienceId;
    }

    public void setExperienceId(UUID id) {
        this.experienceId = id;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
