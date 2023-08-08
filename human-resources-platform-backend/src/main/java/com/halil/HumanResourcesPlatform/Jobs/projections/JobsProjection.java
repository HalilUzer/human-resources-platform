package com.halil.HumanResourcesPlatform.Jobs.projections;

import java.util.UUID;

public interface JobsProjection {

    UUID getJobId();

    String getTitle();
    PosterView getPoster();

    interface PosterView{
        String getHrSpecialistId();
        String getUsername();
    }

}
