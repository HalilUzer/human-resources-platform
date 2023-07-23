package com.halil.HumanResourcesPlatform.Authentication.controllers;

import com.halil.HumanResourcesPlatform.Authentication.security.UserAuthenticationProvider;
import com.halil.HumanResourcesPlatform.Authentication.dtos.SignInDto;
import com.halil.HumanResourcesPlatform.Authentication.services.LinkedinOauthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
public class AuthenticationController {

    UserAuthenticationProvider userAuthenticationProvider;

    LinkedinOauthService linkedinOauthService;




    AuthenticationController(UserAuthenticationProvider userAuthenticationProvider,
                             LinkedinOauthService linkedinOauthService){
        this.userAuthenticationProvider = userAuthenticationProvider;
        this.linkedinOauthService = linkedinOauthService;
    }

    @PostMapping("/sign-in")
    public String signIn(@Valid @RequestBody SignInDto signInDto){
        return userAuthenticationProvider.createToken(signInDto.username());
    }

    @GetMapping("/api")
    public String api(){
        return "Success";
    }

    @PostMapping("/linkedin/access_token")
    public String getLinkedinAccessToken(){
        linkedinOauthService.getAccessTokenFromLinkedinServer();
return null;

    }
}
