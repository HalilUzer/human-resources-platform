package com.halil.HumanResourcesPlatform.HrSpecialists.projections;

import java.util.List;
import java.util.UUID;

public interface PostedJobsProjectory {
    UUID gethrSpecialistId();

    List<JobView> getPostedJobs();

    interface JobView {

    }

}
