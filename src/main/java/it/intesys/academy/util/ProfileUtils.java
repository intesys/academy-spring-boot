package it.intesys.academy.util;

import org.springframework.boot.SpringApplication;

import java.util.HashMap;
import java.util.Map;

public final class ProfileUtils {

    private static final String SPRING_PROFILE_DEFAULT = "spring.profiles.default";
    public static final String DEV_PROFILE = "dev";
    public static final String PROD_PROFILE = "prod";
    public static final String FAKE_DATA_PROFILE = "fake-data";

    private ProfileUtils() {

    }

    public static void addDefaultProfile(SpringApplication app) {

        Map<String, Object> defProperties = new HashMap<>();
        defProperties.put(SPRING_PROFILE_DEFAULT, DEV_PROFILE);
        app.setDefaultProperties(defProperties);
    }
}
