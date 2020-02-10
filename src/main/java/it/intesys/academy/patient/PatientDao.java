package it.intesys.academy.patient;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PatientDao {

    public Patient findById(Long patientId) {

        try (Connection connection = dataSource().getConnection()) {
            //execute query
            PreparedStatement selectStatement = connection.prepareStatement("select id, firstName, lastName from patient where id = ?");
            selectStatement.setLong(1, patientId);
            ResultSet resultSet = selectStatement.executeQuery();

            //map to patient list
            List<Patient> patients = mapToListOfPatients(resultSet);

            //return results
            return patients.isEmpty() ? null : patients.get(0);

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public List<Patient> searchPatient(String searchString) {
        try (Connection connection = dataSource().getConnection()) {
            //execute query
            PreparedStatement selectStatement = connection
                    .prepareStatement("select id, firstName, lastName from patient where lastName like ? or firstName like ?");
            selectStatement.setString(1, "%" + searchString + "%");
            selectStatement.setString(2, "%" + searchString + "%");
            ResultSet resultSet = selectStatement.executeQuery();

            //map to patient list
            List<Patient> patients = mapToListOfPatients(resultSet);

            //return results
            return patients;

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static DataSource dataSource() {
        Properties appProperties = appProperties();
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(appProperties.getProperty("db.url"));
        hikariConfig.setUsername(appProperties.getProperty("db.user"));
        hikariConfig.setPassword(appProperties.getProperty("db.password"));
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        return new HikariDataSource(hikariConfig);
    }


    private static Properties appProperties() {

        Properties prop = new Properties();
        try (InputStream input = PatientDao.class.getClassLoader().getResourceAsStream("application.properties")) {
            prop.load(input);
        } catch (IOException ex) {
            throw new IllegalStateException("Property load fail", ex);
        }
        return prop;
    }

    List<Patient> mapToListOfPatients(ResultSet resultSet) throws SQLException {
        List<Patient> patients = new ArrayList<>();
        while(resultSet.next()) {
            long patientId = resultSet.getLong("id");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            patients.add(new Patient(patientId, firstName, lastName));
        }

        return patients;
    }
}
