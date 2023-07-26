package com.halil.HumanResourcesPlatform.Authentication.services;

import jakarta.annotation.PostConstruct;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeVisitor;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class SeleniumService {

    private final ChromeDriver chromeDriver;

     @PostConstruct
     private void postConstruct(){
         chromeDriver.get("https://www.linkedin.com/login");
     }

    private final Logger logger = LoggerFactory.getLogger(SeleniumService.class);

    SeleniumService(ChromeDriver chromeDriver){
        this.chromeDriver = chromeDriver;
    }

    private void getDataFromLinkedin(){
        chromeDriver.get("https://www.linkedin.com/in/williamhgates/");
        Document document = Jsoup.parse(chromeDriver.getPageSource());
        Elements elements = document.select("span[aria-hidden='true']");
        for(Element element : elements){
            logger.info(element.text());
        }

    }

    private void tempLinkedinLogin(){
        chromeDriver.get("https://www.linkedin.com/login");
        try{
            WebElement usernameElement = chromeDriver.findElement(By.id("username"));
            WebElement passwordElement = chromeDriver.findElement(By.id("password"));
            WebElement loginButton = chromeDriver.findElement(By.className("from__button--floating"));
            usernameElement.sendKeys("arthurniall734@gmail.com");
            passwordElement.sendKeys("ck5464edeo4");
            loginButton.click();
        }
        catch (NoSuchElementException e){}

    }

    public void createCandidateFromLinkedin(){
        getDataFromLinkedin();
    }
}
