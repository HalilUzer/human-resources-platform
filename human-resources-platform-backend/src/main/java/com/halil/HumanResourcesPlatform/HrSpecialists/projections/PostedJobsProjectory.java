package com.halil.HumanResourcesPlatform.HrSpecialists.projections;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.UUID;

public interface PostedJobsProjectory {
    @JsonIgnore
    UUID gethrSpecialistId();

    List<JobView> getPostedJobs();

    String getUsername();

    interface JobView {
        UUID getJobId();

        String getTitle();
    }

}
