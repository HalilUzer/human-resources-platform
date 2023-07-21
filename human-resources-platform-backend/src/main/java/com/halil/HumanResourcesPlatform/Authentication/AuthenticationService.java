package com.halil.HumanResourcesPlatform.Authentication;

import com.halil.HumanResourcesPlatform.HumanResourcesSpecialist.entities.HumanResourcesSpecialist;
import com.halil.HumanResourcesPlatform.HumanResourcesSpecialist.repositories.HumanResourcesRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {




    private final HumanResourcesRepository humanResourcesRepository;

    public AuthenticationService( HumanResourcesRepository humanResourcesRepository) {
        this.humanResourcesRepository = humanResourcesRepository;
    }

    public HumanResourcesSpecialist validateCredentials(HumanResourcesSpecialist humanResourcesSpecialist) throws RuntimeException {
        if (this.humanResourcesRepository.validateCredentials(humanResourcesSpecialist)) {
            return humanResourcesSpecialist;
        }
        throw new RuntimeException("Invalid Password");
    }

    public HumanResourcesSpecialist findByUsername(String username) throws RuntimeException {
        HumanResourcesSpecialist humanResourcesSpecialist = this.humanResourcesRepository.findByUsername(username);
        if (humanResourcesSpecialist != null) {
            return humanResourcesSpecialist;
        }
        throw new RuntimeException("Invalid Username");
    }
}
