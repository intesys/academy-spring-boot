package it.intesys.academy;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import it.intesys.academy.patient.Patient;
import it.intesys.academy.patient.PatientDao;
import it.intesys.academy.patient.PatientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Bean
    public DataSource dataSource(Environment environment) {

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(environment.getProperty("db.url"));
        hikariConfig.setUsername(environment.getProperty("db.user"));
        hikariConfig.setPassword(environment.getProperty("db.password"));
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public PatientService patientService(PatientDao patientDao) {
        return new PatientService(patientDao);
    }

    @Bean
    public PatientDao patientDao(DataSource dataSource) {
        return new PatientDao(dataSource);
    }
}
