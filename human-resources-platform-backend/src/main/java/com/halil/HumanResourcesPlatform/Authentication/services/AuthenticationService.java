package com.halil.HumanResourcesPlatform.Authentication.services;

import com.halil.HumanResourcesPlatform.HrSpecialists.repositories.LdapHrSpecialist;
import com.halil.HumanResourcesPlatform.HrSpecialists.repositories.LdapHrSpecialistRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final LdapHrSpecialistRepository ldapHrSpecialistRepository;


    public AuthenticationService( LdapHrSpecialistRepository ldapHrSpecialistRepository) {
        this.ldapHrSpecialistRepository = ldapHrSpecialistRepository;
    }

    public LdapHrSpecialist validateCredentials(LdapHrSpecialist hrSpecialist) throws RuntimeException {
        if (this.ldapHrSpecialistRepository.validateCredentials(hrSpecialist)) {
            return hrSpecialist;
        }
        throw new RuntimeException("Invalid Password");
    }

    public LdapHrSpecialist findByUsername(String username) throws RuntimeException {
        LdapHrSpecialist hrSpecialist = this.ldapHrSpecialistRepository.findByUsername(username);
        if (hrSpecialist != null) {
            return hrSpecialist;
        }
        throw new RuntimeException("Invalid Username");
    }
}
