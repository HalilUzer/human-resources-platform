package com.halil.HumanResourcesPlatform.Authentication.configs;

import org.springframework.http.HttpMethod;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PathsConfig {

    public final List<Path> hrSpecialistPaths = List.of();
    public final List<Path> candidePaths = List.of(new Path("/linkedin/access-token", HttpMethod.GET));
    public final List<Path> publicPaths = List.of(new Path("/", HttpMethod.GET), new Path("/sign-in", HttpMethod.GET));

    public boolean matcher(List<Path> paths, String path, String method){
        for(Path path1: paths){
            if(path1.equals(path) && path1.method().matches(method)){
                return true;
            }

        }
        return false;
    }
}
