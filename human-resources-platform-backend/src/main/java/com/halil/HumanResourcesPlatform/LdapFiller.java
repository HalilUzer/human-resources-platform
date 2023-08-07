package com.halil.HumanResourcesPlatform;

import com.halil.HumanResourcesPlatform.HrSpecialists.repositories.LdapHrSpecialistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class LdapFiller implements CommandLineRunner {

    private final LdapHrSpecialistRepository ldapHrSpecialistRepository;

    private final Logger logger = LoggerFactory.getLogger(LdapFiller.class);

    public LdapFiller(LdapHrSpecialistRepository ldapHrSpecialistRepository){
        this.ldapHrSpecialistRepository = ldapHrSpecialistRepository;
    }

    @Override
    public void run(String... args)  {
        try{
            ldapHrSpecialistRepository.createOrganizationalUnit();
            ldapHrSpecialistRepository.create("Halil", "123");
            ldapHrSpecialistRepository.create("Qwerty", "567");
        }
        catch (Exception e){
            logger.info("Ldap already filled");
        }

        ldapHrSpecialistRepository.findByUsername("halil");

    }
}
