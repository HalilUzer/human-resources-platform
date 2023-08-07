package com.halil.HumanResourcesPlatform.Jobs.projections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.halil.HumanResourcesPlatform.Applications.entities.ApplicationStatus;

import java.util.List;
import java.util.UUID;

public interface ApplicantProjection {

    @JsonIgnore
    UUID getJobId();


    List<ApplicationView> getApplications();

    interface ApplicationView {
        CandidateView getCandidate();

        UUID getApplicationId();

        ApplicationStatus getStatus();

        interface CandidateView {
            String getName();
            String getSurname();
            UUID getCandidateId();
            String getImageSource();
        }


    }

}
