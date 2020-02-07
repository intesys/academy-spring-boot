package it.intesys.academy;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import it.intesys.academy.patient.PatientDao;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {

    public static Properties appProperties;
    public static DataSource dataSource;

    public static DataSource dataSource() {

        if(dataSource == null) {
            Properties appProperties = appProperties();
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(appProperties.getProperty("db.url"));
            hikariConfig.setUsername(appProperties.getProperty("db.user"));
            hikariConfig.setPassword(appProperties.getProperty("db.password"));
            hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource = new HikariDataSource(hikariConfig);
        }

        return dataSource;
    }


    private static Properties appProperties() {
        if (appProperties == null) {
            Properties prop = new Properties();
            try (InputStream input = PatientDao.class.getClassLoader().getResourceAsStream("application.properties")) {
                prop.load(input);
            } catch (IOException ex) {
                throw new IllegalStateException("Property load fail", ex);
            }
            appProperties = prop;
        }

        return appProperties;
    }
}
