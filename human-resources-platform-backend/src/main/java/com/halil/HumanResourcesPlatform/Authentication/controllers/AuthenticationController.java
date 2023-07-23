package com.halil.HumanResourcesPlatform.Authentication.controllers;

import com.halil.HumanResourcesPlatform.Authentication.services.UserAuthenticationProvider;
import com.halil.HumanResourcesPlatform.Authentication.dtos.SignInDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController {

    UserAuthenticationProvider userAuthenticationProvider;


    AuthenticationController(UserAuthenticationProvider userAuthenticationProvider){
        this.userAuthenticationProvider = userAuthenticationProvider;
    }

    @PostMapping("/sign-in")
    public String signIn(@Valid @RequestBody SignInDto signInDto){
        return userAuthenticationProvider.createToken(signInDto.username());
    }

    @GetMapping("/api")
    public String api(){

        return "Success";
    }
}
