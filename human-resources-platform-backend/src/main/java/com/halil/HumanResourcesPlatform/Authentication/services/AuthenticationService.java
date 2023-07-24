package com.halil.HumanResourcesPlatform.Authentication.services;

import com.halil.HumanResourcesPlatform.HrSpecialist.entities.HrSpecialist;
import com.halil.HumanResourcesPlatform.HrSpecialist.repositories.HrSpecialistRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final HrSpecialistRepository hrSpecialistRepository;

    public AuthenticationService( HrSpecialistRepository hrSpecialistRepository) {
        this.hrSpecialistRepository = hrSpecialistRepository;
    }

    public HrSpecialist validateCredentials(HrSpecialist hrSpecialist) throws RuntimeException {
        if (this.hrSpecialistRepository.validateCredentials(hrSpecialist)) {
            return hrSpecialist;
        }
        throw new RuntimeException("Invalid Password");
    }

    public HrSpecialist findByUsername(String username) throws RuntimeException {
        HrSpecialist hrSpecialist = this.hrSpecialistRepository.findByUsername(username);
        if (hrSpecialist != null) {
            return hrSpecialist;
        }
        throw new RuntimeException("Invalid Username");
    }
}
