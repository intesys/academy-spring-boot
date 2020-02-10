package it.intesys.academy.util;

import org.springframework.boot.SpringApplication;

import java.util.HashMap;
import java.util.Map;

public final class DefaultProfileUtils {

    private static final String SPRING_PROFILE_DEFAULT = "spring.profiles.default";

    private DefaultProfileUtils() {

    }

    public static void addDefaultProfile(SpringApplication app) {

        Map<String, Object> defProperties = new HashMap<>();
        defProperties.put(SPRING_PROFILE_DEFAULT, "dev");
        app.setDefaultProperties(defProperties);
    }
}
