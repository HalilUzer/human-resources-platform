package com.halil.HumanResourcesPlatform.Authentication.configs;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class PathsConfig {

    public final List<Path> hrSpecialistPaths = List.of();
    public final List<Path> candidatePaths = List.of(new Path("/linkedin/access-token", HttpMethod.GET), new Path("/linkedin/build", HttpMethod.POST));


    public static List<String> getAllPublicPaths(List<Path> paths){
        List<String> paths2 = new ArrayList<String>();
        for(Path path : paths){
            paths2.add(path.path());
        }
        return paths2;
    }

    public boolean pathAndRoleMatcher(Roles role, String givenPath){

        if (role.equals(Roles.CANDIDATE)) {
            for (Path path : candidatePaths) {
                if (path.path().equals(givenPath)) {
                    return true;
                }
            }
            return  false;
        }
        if (role.equals(Roles.HR_SPECIALIST)) {
            for (Path path : hrSpecialistPaths) {
                if (path.path().equals(givenPath)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }


    public boolean pathAndMethodMatcher(List<Path> paths, String path, String method) {
        for (Path path1 : paths) {
            if (path1.equals(path) && path1.method().matches(method)) {
                return true;
            }
        }
        return false;
    }
}
