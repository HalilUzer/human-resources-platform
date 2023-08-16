package com.halil.HumanResourcesPlatform.Jobs.services;

import com.halil.HumanResourcesPlatform.Applications.services.ApplicationService;
import com.halil.HumanResourcesPlatform.HrSpecialists.entities.HrSpecialist;
import com.halil.HumanResourcesPlatform.HrSpecialists.repositories.HrSpecialistRepository;
import com.halil.HumanResourcesPlatform.Jobs.dtos.CreateJobDto;
import com.halil.HumanResourcesPlatform.Jobs.entities.Job;
import com.halil.HumanResourcesPlatform.Jobs.entities.PersonalSkill;
import com.halil.HumanResourcesPlatform.Jobs.entities.Status;
import com.halil.HumanResourcesPlatform.Jobs.entities.TechnicalSkill;
import com.halil.HumanResourcesPlatform.Jobs.projections.ApplicantProjection;
import com.halil.HumanResourcesPlatform.Jobs.projections.JobProjection;
import com.halil.HumanResourcesPlatform.Jobs.repositories.JobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.UUID;

@Service
public class JobService {

    private final HrSpecialistRepository hrSpecialistRepository;
    private final JobRepository jobRepository;
    private final Logger logger = LoggerFactory.getLogger(JobService.class);

    public JobService(HrSpecialistRepository hrSpecialistRepository,
                      JobRepository jobRepository,
                      ApplicationService applicationService) {
        this.hrSpecialistRepository = hrSpecialistRepository;
        this.jobRepository = jobRepository;

    }

    public Job saveJobFromDto(CreateJobDto dto, UUID hrSpecialistId) {
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
        jobRepository.save(job);
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
        logger.info("Job: " + job.getJobId() + " status changed to" +  newStatus.name());
    }


    public Job createJob(CreateJobDto createJobDto, UUID hrSpecialistId){
        Job job = this.saveJobFromDto(createJobDto, hrSpecialistId);
        HrSpecialist hrSpecialist = hrSpecialistRepository.findById(hrSpecialistId).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Hr specialist not found"));
        hrSpecialist.pushJob(job);
        hrSpecialistRepository.save(hrSpecialist);
        logger.info("Job: " + job.getJobId() + "created");
        return job;
    }


    public JobProjection getJob(UUID jobId){
        return jobRepository.getJobProjectionByJobId(jobId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job didnt found"));
    }


    public ApplicantProjection getApplicantsByJobId(UUID jobId){
        return jobRepository.getApplicantsByJobId(jobId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job didnt found"));
    }


}
