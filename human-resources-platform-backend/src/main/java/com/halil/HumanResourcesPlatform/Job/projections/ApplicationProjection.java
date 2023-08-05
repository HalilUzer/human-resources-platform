package com.halil.HumanResourcesPlatform.Job.projections;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.UUID;

public interface ApplicationProjection {

    @JsonIgnore
    UUID getJobId();
    List<ApplicantsView> getApplicants();

    interface ApplicantsView {
        String getCandidateId();
        String getName();
        String getSurname();
    }
}
