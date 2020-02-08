package it.intesys.academy;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import it.intesys.academy.patient.Patient;
import it.intesys.academy.patient.PatientDao;
import it.intesys.academy.patient.PatientService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class AppConfig {

    @Bean
    public DataSource dataSource() {

        Properties appProperties = appProperties();
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(appProperties.getProperty("db.url"));
        hikariConfig.setUsername(appProperties.getProperty("db.user"));
        hikariConfig.setPassword(appProperties.getProperty("db.password"));
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public Properties appProperties() {
        Properties prop = new Properties();
        try (InputStream input = PatientDao.class.getClassLoader().getResourceAsStream("application.properties")) {
            prop.load(input);
        } catch (IOException ex) {
            throw new IllegalStateException("Property load fail", ex);
        }
        return prop;
    }

    @Bean
    public PatientService patientService() {
        return new PatientService(patientDao());
    }

    @Bean
    public PatientDao patientDao() {
        return new PatientDao(dataSource());
    }
}
