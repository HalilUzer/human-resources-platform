package com.halil.HumanResourcesPlatform.Jobs.services;

import com.halil.HumanResourcesPlatform.HrSpecialists.entities.HrSpecialist;
import com.halil.HumanResourcesPlatform.HrSpecialists.repositories.HrSpecialistRepository;
import com.halil.HumanResourcesPlatform.Jobs.entities.Job;
import com.halil.HumanResourcesPlatform.Jobs.entities.PersonalSkill;
import com.halil.HumanResourcesPlatform.Jobs.entities.Status;
import com.halil.HumanResourcesPlatform.Jobs.entities.TechnicalSkill;
import com.halil.HumanResourcesPlatform.Jobs.projections.ApplicantProjection;
import com.halil.HumanResourcesPlatform.Jobs.projections.JobProjection;
import com.halil.HumanResourcesPlatform.Jobs.repositories.JobRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class JobService {

    private final HrSpecialistRepository hrSpecialistRepository;
    private final JobRepository jobRepository;
    private final Logger logger = LoggerFactory.getLogger(JobService.class);

    private final JdbcTemplate jdbcTemplate;

    public JobService(HrSpecialistRepository hrSpecialistRepository,
                      JobRepository jobRepository,
                      JdbcTemplate jdbcTemplate) {
        this.hrSpecialistRepository = hrSpecialistRepository;
        this.jobRepository = jobRepository;
        this.jdbcTemplate = jdbcTemplate;

    }

    public Job saveJob(UUID hrSpecialistId, String title, String jobDescription, Status status, LocalDateTime until,
                       List<String> technicalSkills, List<String> personalSkills, boolean isPermanent) {
        HrSpecialist hrSpecialist = hrSpecialistRepository.findById(hrSpecialistId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hr specialist not found"));

        List<TechnicalSkill> technicalSkillsEntities = new ArrayList<>();
        for(String technicalSkill : technicalSkills){
            technicalSkillsEntities.add(new TechnicalSkill(technicalSkill));
        }

        List<PersonalSkill> personalSkillsEntities = new ArrayList<>();
        for(String personalSkill: personalSkills){
            personalSkillsEntities.add(new PersonalSkill(personalSkill));
        }

        Job job = new Job(hrSpecialist, technicalSkillsEntities, personalSkillsEntities, title, until, status, jobDescription,
                isPermanent);

        hrSpecialistRepository.save(hrSpecialist);
        jobRepository.save(job);
        logger.info("Job: " + job.getJobId() + " created");
        return job;
    }

    public void changeJobStatus(UUID jobId, UUID hrSpecialistId, Status newStatus, String until, boolean isPermanent) {
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job didnt found"));

        if (!job.getPoster().getHrSpecialistId().equals(hrSpecialistId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        if (isPermanent) {
            job.setStatus(newStatus);
        } else {
            LocalDateTime localDateTime = parseToLocalDateTime(until, false);
            createChangeJobStatusEvent(jobId, newStatus, localDateTime);
        }

        jobRepository.save(job);
        logger.info("Job: " + job.getJobId() + " status changed to " + newStatus.name());
    }


    public void createChangeJobStatusEvent(UUID jobId, Status newStatus, LocalDateTime until) {
        String date = until.getYear() + "-" + until.getMonthValue() + "-" + until.getDayOfMonth();
        String hour = until.getHour() + ":" + until.getMinute() + ":00";

        String sql = "CREATE EVENT IF NOT EXISTS changeStatus\n" +
                "ON SCHEDULE AT ? ?\n" +
                "DO\n" +
                "UPDATE jobs WHERE job_id=? SET status=?";

        jdbcTemplate.execute(sql, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps)
                    throws SQLException, DataAccessException {
                ps.setString(1, date);
                ps.setString(2, hour);
                ps.setString(3, jobId.toString());
                ps.setInt(4, newStatus.ordinal());
                return ps.execute();
            }
        });
    }

    /*"CREATE EVENT IF NOT EXISTS changeStatus" +
            "            "ON SCHEDULE AT ':year-:month-:day :hour::minute:00'"
            "            \"DO\\n\" +\n" +
            "            \"UPDATE jobs WHERE job_id=:job_id SET status=:status\")"*/

    public LocalDateTime parseToLocalDateTime(String date, boolean isPermanent) {
        String[] times = date.split(" ");
        try {
            LocalDateTime nowPlusOneHour = LocalDateTime.now().plusHours(1);
            LocalDateTime localDateTime = LocalDateTime.of(Integer.parseInt(times[2]), Integer.parseInt(times[1]), Integer.parseInt(times[0]), Integer.parseInt(times[3]), Integer.parseInt(times[4]));
            if(!localDateTime.isAfter(nowPlusOneHour) && !isPermanent){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datetime not after 1 hour from current time");
            }
            return localDateTime;
        } catch (DateTimeException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date time");
        }
    }



    public void checkStatus(Job job){
        if(LocalDateTime.now().isAfter(job.getUntil())){
            if(job.getStatus().equals(Status.ACTIVE)){
                job.setStatus(Status.PASSIVE);
            }
            else{
                job.setStatus(Status.ACTIVE);
            }
        }
        jobRepository.save(job);
    }


    public Job getJob(UUID jobId) {
        Job job = jobRepository.getJobByJobId(jobId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job didnt found"));
        if(job.isPermanent() == false){
            checkStatus(job);
        }
        return job;
    }
    public JobProjection getJobProjection(UUID jobId){
        return jobRepository.getJobProjectionByJobId(jobId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job didnt found"));
    }


    public ApplicantProjection getApplicantsByJobId(UUID jobId) {
        return jobRepository.getApplicantsByJobId(jobId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job didnt found"));
    }


}
