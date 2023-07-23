package com.halil.HumanResourcesPlatform.HumanResourcesSpecialist.repositories;

import com.halil.HumanResourcesPlatform.Authentication.services.AuthenticationService;
import com.halil.HumanResourcesPlatform.HumanResourcesSpecialist.entities.HumanResourcesSpecialist;

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

@Service
public class HumanResourcesRepository {
    LdapTemplate template;
    LdapContextSource context;

    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public HumanResourcesRepository(LdapTemplate template, LdapContextSource context){
        this.template = template;
        this.context = context;
    }

    public boolean validateCredentials(HumanResourcesSpecialist humanResourcesSpecialist){
        List<byte[]> result = template.search("","uid=" + humanResourcesSpecialist.username, (AttributesMapper<byte[]>) attrs -> (byte[]) attrs.get("userPassword").get());

        if(result.size() == 0){
            return false;
        }
        String password = new String(result.get(0), StandardCharsets.UTF_8);
        if(!password.equals(humanResourcesSpecialist.password)){
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
    }

    public HumanResourcesSpecialist findByUsername(String username){
        List<byte[]> result = template.<byte[]>search("", "uid=" + username, (AttributesMapper<byte[]>) attrs -> (byte[]) attrs.get("userPassword").get());
        String password = new String(result.get(0), StandardCharsets.UTF_8);
        if(result.size() == 0){
            return null;
        }
        else{
            return new HumanResourcesSpecialist(null, null, username, password);
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
