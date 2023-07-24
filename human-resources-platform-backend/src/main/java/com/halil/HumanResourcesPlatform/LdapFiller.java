package com.halil.HumanResourcesPlatform;

import com.halil.HumanResourcesPlatform.HrSpecialist.repositories.HrSpecialistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class LdapFiller implements CommandLineRunner {

    private final HrSpecialistRepository hrSpecialistRepository;

    private final Logger logger = LoggerFactory.getLogger(LdapFiller.class);

    public LdapFiller(HrSpecialistRepository hrSpecialistRepository){
        this.hrSpecialistRepository = hrSpecialistRepository;
    }

    @Override
    public void run(String... args)  {
        try{
            hrSpecialistRepository.createOrganizationalUnit();
            hrSpecialistRepository.create("Halil", "123");
            hrSpecialistRepository.create("Qwerty", "567");
        }
        catch (Exception e){
            logger.info("Ldap already filled");
        }

        hrSpecialistRepository.findByUsername("halil");

    }
}
