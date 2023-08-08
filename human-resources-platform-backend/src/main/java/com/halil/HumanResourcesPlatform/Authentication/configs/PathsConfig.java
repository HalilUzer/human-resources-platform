package com.halil.HumanResourcesPlatform.Authentication.configs;

import org.springframework.http.HttpMethod;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class PathsConfig {

    public final List<Path> hrSpecialistPaths = List.of(new Path("/candidate/(.*)/applications", HttpMethod.GET), new Path("/hr-specialist/(.*)/posts", HttpMethod.GET),
            new Path("/job", HttpMethod.POST), new Path("/job/(.*)/apply", HttpMethod.PUT), new Path("/job/(.*)/status", HttpMethod.PUT),
            new Path("/job/(.*)/applicants", HttpMethod.GET), new Path("/hr-specialist/(.*)/posts", HttpMethod.GET));

    public final List<Path> candidatePaths = List.of(new Path("/linkedin/build", HttpMethod.POST),
            new Path("/job/(.*)/apply", HttpMethod.PUT), new Path("/candidate/(.*)/applications", HttpMethod.GET));


    public static List<String> getAllPublicPaths(List<Path> paths) {
        List<String> paths2 = new ArrayList<String>();
        for (Path path : paths) {
            paths2.add(path.path());
        }
        return paths2;
    }

    public boolean pathAndRoleMatcher(Roles role, String givenPath) {

        if (role.equals(Roles.CANDIDATE)) {
            for (Path path : candidatePaths) {
                if (givenPath.matches(path.path())) {
                    return true;
                }
            }
            return false;
        }
        if (role.equals(Roles.HR_SPECIALIST)) {
            for (Path path : hrSpecialistPaths) {
                if (givenPath.matches(path.path())) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }


    public boolean pathAndMethodMatcher(List<Path> paths, String path, String method) {
        for (Path path1 : paths) {
            if (path.matches(path1.path())) {
                if(path1.method().matches(method))
                return true;
            }
        }
        return false;
    }
}
