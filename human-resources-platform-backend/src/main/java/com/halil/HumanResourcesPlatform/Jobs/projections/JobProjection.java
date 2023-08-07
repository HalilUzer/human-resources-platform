package com.halil.HumanResourcesPlatform.Jobs.projections;


import com.halil.HumanResourcesPlatform.Jobs.entities.PersonalSkill;
import com.halil.HumanResourcesPlatform.Jobs.entities.Status;
import com.halil.HumanResourcesPlatform.Jobs.entities.TechnicalSkill;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface JobProjection {

    UUID getJobId();
    String getTitle();
    Date getUntil();

    Status getStatus();
    String getJobDescription();

    PosterView getPoster();

    List<TechnicalSkill> getTechnicalSkills();
    List<PersonalSkill> getPersonalSkills();

    interface PosterView{
        String getHrSpecialistId();
        String getUsername();
    }

    interface PersonalSkillView {
        String getPersonalSkill();
    }
    interface TechnicalSkillView {
        String getTechnicalSkill();
    }
}
