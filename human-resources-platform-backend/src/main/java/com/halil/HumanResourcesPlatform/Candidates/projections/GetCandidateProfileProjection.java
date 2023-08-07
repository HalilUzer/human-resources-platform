package com.halil.HumanResourcesPlatform.Candidates.projections;

import java.util.List;
import java.util.UUID;

public interface GetCandidateProfileProjection {
    UUID getCandidateId();

    String getAbout();

    String getName();
    String getSurname();
    String getEmail();
    String getImageSource();

    List<ExperienceView> getExperiences();
    List<EducationView> getEducations();

    interface EducationView {
        String getDate();
        String getUniversity();
        String getChapter();
    }

    interface ExperienceView {
        String getTitle();
        String getDate();
        String getCompany();
        String getDescription();

    }

}
