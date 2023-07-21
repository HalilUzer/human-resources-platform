package com.halil.HumanResourcesPlatform;

import com.halil.HumanResourcesPlatform.HumanResourcesSpecialist.repositories.HumanResourcesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class LdapFiller implements CommandLineRunner {

    private final HumanResourcesRepository humanResourcesRepository;

    private final Logger logger = LoggerFactory.getLogger(LdapFiller.class);

    public LdapFiller(HumanResourcesRepository humanResourcesRepository){
        this.humanResourcesRepository = humanResourcesRepository;
    }

    @Override
    public void run(String... args)  {
        try{
            humanResourcesRepository.createOrganizationalUnit();
            humanResourcesRepository.create("Halil", "123");
            humanResourcesRepository.create("Qwerty", "567");
        }
        catch (Exception e){
            logger.info("Ldap already filled");
        }

        humanResourcesRepository.findByUsername("halil");

    }
}
