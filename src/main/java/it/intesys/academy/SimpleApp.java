package it.intesys.academy;

import it.intesys.academy.config.ServerProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ ServerProperties.class })
public class SimpleApp {

    public static void main(String[] args) {

        SpringApplication.run(SimpleApp.class, args);
    }

}
