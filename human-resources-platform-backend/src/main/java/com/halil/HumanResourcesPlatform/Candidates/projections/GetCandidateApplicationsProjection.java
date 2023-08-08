package com.halil.HumanResourcesPlatform.Candidates.projections;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.UUID;

public interface GetCandidateApplicationsProjection {


    @JsonIgnore
    UUID getCandidateId();

    List<ApplicationsView> getApplications();


    interface ApplicationsView{

        JobView getJob();

        String getStatus();

            interface JobView {

                UUID getJobId();
                String getTitle();
                PosterView getPoster();

                interface PosterView {
                    String getUsername();
                }
            }
    }

}
