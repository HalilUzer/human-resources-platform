package com.halil.HumanResourcesPlatform.HrSpecialists.repositories;

import com.halil.HumanResourcesPlatform.Authentication.services.AuthenticationService;
import com.halil.HumanResourcesPlatform.HrSpecialists.entities.HrSpecialist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;
import javax.naming.Name;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Service
public class LdapHrSpecialistRepository {
    private final LdapTemplate template;
    private final LdapContextSource context;
    private final HrSpecialistRepository hrSpecialistRepository;

    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public LdapHrSpecialistRepository(LdapTemplate template, LdapContextSource context, HrSpecialistRepository hrSpecialistRepository){
        this.template = template;
        this.context = context;
        this.hrSpecialistRepository = hrSpecialistRepository;
    }

    public boolean validateCredentials(LdapHrSpecialist hrSpecialist){
        List<byte[]> result = template.search("","uid=" + hrSpecialist.username(), (AttributesMapper<byte[]>) attrs -> (byte[]) attrs.get("userPassword").get());

        if(result.size() == 0){
            return false;
        }
        String password = new String(result.get(0), StandardCharsets.UTF_8);
        if(!password.equals(hrSpecialist.password())){
            return false;
        }
        return true;
    }

    public void create(String username, String password){
        Name dn = LdapNameBuilder
                .newInstance()
                .add("ou", "users")
                .add("uid", username)
                .build();
        DirContextAdapter context = new DirContextAdapter(dn);

        context.setAttributeValues(
                "objectclass",
                new String[]
                        { "top",
                                "person",
                                "organizationalPerson",
                                "inetOrgPerson" });
        context.setAttributeValue("cn", username);
        context.setAttributeValue("sn", username);
        context.setAttributeValue("uid", username);
        context.setAttributeValue
                ("userPassword", password);

        context.getAttributes();

        template.bind(context);

        HrSpecialist hrSpecialist = new HrSpecialist();
        hrSpecialist.setHrSpecialistId(UUID.randomUUID());
        hrSpecialist.setUsername(username);
        hrSpecialistRepository.save(hrSpecialist);


    }

    public LdapHrSpecialist findByUsername(String username){
        List<byte[]> result = template.<byte[]>search("", "uid=" + username, (AttributesMapper<byte[]>) attrs -> (byte[]) attrs.get("userPassword").get());
        String password = new String(result.get(0), StandardCharsets.UTF_8);
        if(result.size() == 0){
            return null;
        }
        else{
            return new LdapHrSpecialist(username, password);
        }
    }


    public void createOrganizationalUnit(){
        Name dn = LdapNameBuilder
                .newInstance()
                .add("ou", "users")
                .build();
        DirContextAdapter context = new DirContextAdapter(dn);

        context.setAttributeValues(
                "objectclass",
                new String[]
                        { "top",
                                "organizationalUnit"});
        context.setAttributeValue("ou", "users");
        template.bind(context);
    }


}


