package it.intesys.academy;

import it.intesys.academy.config.ApplicationProperties;
import it.intesys.academy.config.ServerProperties;
import it.intesys.academy.util.ProfileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ ServerProperties.class, ApplicationProperties.class })
public class SimpleApp {

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(SimpleApp.class);
        ProfileUtils.addDefaultProfile(app);
        app.run(args);
    }

}
