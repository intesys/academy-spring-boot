package it.intesys.academy;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import it.intesys.academy.patient.Patient;
import it.intesys.academy.patient.PatientDao;
import it.intesys.academy.patient.PatientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan
public class AppConfig {

    @Bean
    public JdbcTemplate dataSource(Environment environment) {

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(environment.getProperty("db.url"));
        hikariConfig.setUsername(environment.getProperty("db.user"));
        hikariConfig.setPassword(environment.getProperty("db.password"));
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        return new JdbcTemplate(dataSource);
    }

}
