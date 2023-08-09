package com.halil.HumanResourcesPlatform.Jobs.services;

import com.halil.HumanResourcesPlatform.Applications.services.ApplicationService;
import com.halil.HumanResourcesPlatform.HrSpecialists.entities.HrSpecialist;
import com.halil.HumanResourcesPlatform.HrSpecialists.repositories.HrSpecialistRepository;
import com.halil.HumanResourcesPlatform.Jobs.dtos.CreateJobDto;
import com.halil.HumanResourcesPlatform.Jobs.entities.Job;
import com.halil.HumanResourcesPlatform.Jobs.entities.PersonalSkill;
import com.halil.HumanResourcesPlatform.Jobs.entities.Status;
import com.halil.HumanResourcesPlatform.Jobs.entities.TechnicalSkill;
import com.halil.HumanResourcesPlatform.Jobs.repositories.JobRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.UUID;

@Service
public class JobService {

    private final HrSpecialistRepository hrSpecialistRepository;

    private final JobRepository jobRepository;




    public JobService(HrSpecialistRepository hrSpecialistRepository,
                      JobRepository jobRepository,
                      ApplicationService applicationService) {
        this.hrSpecialistRepository = hrSpecialistRepository;
        this.jobRepository = jobRepository;

    }

    public Job buildJobFromDto(CreateJobDto dto, UUID hrSpecialistId) {
        Job job = new Job();
        HrSpecialist hrSpecialist = hrSpecialistRepository.findById(hrSpecialistId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hr specialist not found"));
        job.setPoster(hrSpecialist);

        for (String technicalSkill : dto.technical_skills()) {
            job.addTechnicalSkills(new TechnicalSkill(technicalSkill));
        }

        for (String personalSkill : dto.personal_skills()) {
            job.addPersonalSkill(new PersonalSkill(personalSkill));
        }

        job.setTitle(dto.title());
        job.setUntil(dto.until());
        job.setStatus(dto.status());
        job.setJobDescription(dto.job_description());

        return job;
    }

    public Job checkStatus(Job job){
        Date current = new Date();
        Date jobEndTime = job.getUntil();

        if(jobEndTime.before(current)){
            if (job.getStatus().equals(Status.ACTIVE)) {
                job.setStatus(Status.PASSIVE);
            } else if (job.getStatus().equals(Status.PASSIVE)) {
                job.setStatus(Status.ACTIVE);
            }

        }
        jobRepository.save(job);
        return job;
    }

    public void changeJobStatus(UUID jobId, UUID hrSpecialistId, Status newStatus, Date until){
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job didnt found"));
        if (!job.getPoster().getHrSpecialistId().equals(hrSpecialistId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        job.setStatus(newStatus);
        job.setUntil(until);
        jobRepository.save(job);
    }


    public Job createJob(CreateJobDto createJobDto, UUID hrSpecialistId){
        Job job = this.buildJobFromDto(createJobDto, hrSpecialistId);
        HrSpecialist hrSpecialist = hrSpecialistRepository.findById(hrSpecialistId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hr specialist not found"));
        hrSpecialist.pushJob(job);
        hrSpecialistRepository.save(hrSpecialist);
        jobRepository.save(job);
        return job;
    }


}
