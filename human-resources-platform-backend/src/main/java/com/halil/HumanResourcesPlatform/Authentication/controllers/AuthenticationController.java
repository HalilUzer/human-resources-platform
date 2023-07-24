package com.halil.HumanResourcesPlatform.Authentication.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.halil.HumanResourcesPlatform.Authentication.configs.Roles;
import com.halil.HumanResourcesPlatform.Authentication.dtos.LinkedinSignInDto;
import com.halil.HumanResourcesPlatform.Authentication.security.AuthenticationProvider;
import com.halil.HumanResourcesPlatform.Authentication.dtos.SignInDto;
import com.halil.HumanResourcesPlatform.Authentication.services.LinkedinOauthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
public class AuthenticationController {

    AuthenticationProvider authenticationProvider;

    LinkedinOauthService linkedinOauthService;

    ObjectMapper OBJECT_MAPPER;



    AuthenticationController(AuthenticationProvider authenticationProvider,
                             LinkedinOauthService linkedinOauthService,
                             ObjectMapper OBJECT_MAPPER){
        this.authenticationProvider = authenticationProvider;
        this.linkedinOauthService = linkedinOauthService;
        this.OBJECT_MAPPER = OBJECT_MAPPER;
    }

    @PostMapping("/sign-in")
    public String signIn(@Valid @RequestBody SignInDto signInDto){
        return authenticationProvider.createToken(signInDto.username(), Roles.HR_SPECIALIST.toString());
    }

    @GetMapping("/api")
    public String api(){
        return "Success";
    }

    @PostMapping("/linkedin/sign-in")
    @ResponseStatus(HttpStatus.CREATED)
    public void linkedinSignIn(@Valid @RequestBody LinkedinSignInDto linkedinSignInDto){
        String accessToken = linkedinOauthService.getAccessTokenFromLinkedin(linkedinSignInDto.code());
        linkedinOauthService.createProfileFromLinkedin(accessToken);
    }
}
