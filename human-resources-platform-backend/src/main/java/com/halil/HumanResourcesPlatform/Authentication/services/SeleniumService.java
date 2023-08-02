package com.halil.HumanResourcesPlatform.Authentication.services;

import com.halil.HumanResourcesPlatform.Candidate.entites.Candidate;
import com.halil.HumanResourcesPlatform.Candidate.entites.Education;
import com.halil.HumanResourcesPlatform.Candidate.entites.Experience;
import com.halil.HumanResourcesPlatform.Candidate.repositories.CandidateRepository;
import jakarta.annotation.PostConstruct;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.Duration;



@Service
public class SeleniumService {

    private final ChromeDriver chromeDriver;

    @PostConstruct
    private void postConstruct() {
        chromeDriver.get("https://www.linkedin.com/login");
    }

    private final Logger logger = LoggerFactory.getLogger(SeleniumService.class);

    private final CandidateRepository candidateRepository;

    SeleniumService(ChromeDriver chromeDriver,
                    CandidateRepository candidateRepository) {
        this.chromeDriver = chromeDriver;
        this.candidateRepository = candidateRepository;
    }




    private Candidate getAboutFromLinkedin(Candidate candidate) {
        String data;
        try {
            data = chromeDriver.findElement(By.xpath("//*[@id='about']/../div[3]")).getText();
        } catch (NoSuchElementException e) {
            return null;
        }
        candidate.setAbout(data);
        return  candidate;
    }

    private Education getEducationRowFromLinkedin(int index) {
        WebElement university;
        WebElement chapter = null;
        WebElement date = null;
        Education education = new Education();
        try{
            university = chromeDriver.findElement(By.xpath("//*[@id='education']/../div[3]/ul/li[" + index + "]/div/div[2]/div[1]/a/div/div/div/div/span[1]"));
            education.setUniversity(university.getText());
        }
        catch (NoSuchElementException e){
            return null;
        }
        try{
            chapter = chromeDriver.findElement(By.xpath("//*[@id=\"education\"]/../div[3]/ul/li[" + index + "]/div/div[2]/div[1]/a/span[1]/span[1]"));
            education.setChapter(chapter.getText());
        }
        catch (NoSuchElementException e){
        }
        try{
            date = chromeDriver.findElement(By.xpath("//*[@id=\"education\"]/../div[3]/ul/li[" + index + "]/div/div[2]/div[1]/a/span[2]/span[1]"));
            education.setDate(date.getText());
        }
        catch (NoSuchElementException e){}

        return education;
    }

    private Candidate getEducationFromLinkedin(Candidate candidate) {
        for (int i = 1; i < 10; i++) {
            Education education = getEducationRowFromLinkedin(i);
            if(education == null) break;
            education.setCandidate(candidate);
            candidate.pushEducation(getEducationRowFromLinkedin(i));
        }
        return candidate;
    }


    private Experience getExperienceRowFromLinkedin(int index) {
        WebElement description;
        WebElement title;
        Experience experience = new Experience();
        try {
            title = chromeDriver.findElement(By.xpath("//*[@id=\"experience\"]/../div[3]/ul/li[" + index + "]/div/div[2]/div[1]/div[1]/div/div/div/div/span[2]"));
            experience.setTitle(title.getText());

        } catch (Exception e) {
            return null;
        }
        WebElement company = chromeDriver.findElement(By.xpath("//*[@id=\"experience\"]/../div[3]/ul/li[" + index + "]/div/div[2]/div[1]/div[1]/span[1]/span[1]"));
        experience.setCompany(company.getText());
        WebElement date = chromeDriver.findElement(By.xpath("//*[@id=\"experience\"]/../div[3]/ul/li[" + index + "]/div/div[2]/div[1]/div[1]/span[2]/span[1]"));
        experience.setDate(date.getText());
        try {
            description = chromeDriver.findElement(By.xpath("//*[@id=\"experience\"]/../div[3]/ul/li[" + index + "]/div/div[2]/div[2]/ul/li[2]"));
            experience.setDescription(description.getText());
        } catch (Exception e) {
            description = chromeDriver.findElement(By.xpath("//*[@id=\"experience\"]/../div[3]/ul/li[" + index + "]/div/div[2]/div[2]/ul/li[1]"));
            experience.setDescription(description.getText());
        }
        return experience;
    }

    private Candidate getExperienceFromLinkedin(Candidate candidate) {
        for (int i = 1; i < 10; i++) {
            Experience experience = getExperienceRowFromLinkedin(i);
            if(experience == null) break;
            experience.setCandidate(candidate);
            candidate.pushExperience(experience);
        }
        return candidate;
    }

    private void tempLinkedinLogin() {
        chromeDriver.get("https://www.linkedin.com/login");
        try {
            WebElement usernameElement = chromeDriver.findElement(By.id("username"));
            WebElement passwordElement = chromeDriver.findElement(By.id("password"));
            WebElement loginButton = chromeDriver.findElement(By.className("from__button--floating"));
            usernameElement.sendKeys("arthurniall734@gmail.com");
            passwordElement.sendKeys("ck5464edeo4");
            loginButton.click();
        } catch (NoSuchElementException e) {
        }
    }

    public Candidate fillCandidateDataFromLinkedin(Candidate candidate) {
        chromeDriver.get(candidate.getProfileUrl());
        chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        candidate = getExperienceFromLinkedin(candidate);
        candidate = getAboutFromLinkedin(candidate);
        candidate = getEducationFromLinkedin(candidate);
        return candidate;
    }
}
