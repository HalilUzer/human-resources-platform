package com.halil.HumanResourcesPlatform.Authentication.configs;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeleniumConfigs {



     @Bean
    public ChromeDriver chromeDriver(){
         WebDriverManager.chromedriver().setup();
         return new ChromeDriver();
     }
}
