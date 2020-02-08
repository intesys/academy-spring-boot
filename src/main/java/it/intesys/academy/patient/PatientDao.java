package it.intesys.academy.patient;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PatientDao {

    private JdbcTemplate jdbcTemplate;

    public PatientDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Patient findById(Long patientId) {
        return jdbcTemplate.queryForObject("select id, firstName, lastName from patient where id = ?",
                new Object[]{ patientId }, new BeanPropertyRowMapper<>(Patient.class));
    }

    public List<Patient> searchPatient(String searchString) {
        String search = "%" + searchString + "%";
        return jdbcTemplate.query("select id, firstName, lastName from patient where lastName like ? or firstName like ?",
                new Object[]{searchString, search}, new BeanPropertyRowMapper<>(Patient.class));
    }

}
