package it.intesys.academy.patient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
class PatientJdcbDao implements PatientDao {

    private static Logger logger = LoggerFactory.getLogger(PatientJdcbDao.class);
    private NamedParameterJdbcTemplate jdbcTemplate;

    public PatientJdcbDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Patient findById(Long patientId) {
        logger.info("Fetching patient {} via JDBCTemplate", patientId);
        return jdbcTemplate.queryForObject(
                "select id, firstName, lastName, birthDate, fiscalCode from patient where id = :patientId",
                Map.of("patientId", patientId),
                new BeanPropertyRowMapper<>(Patient.class)
        );
    }

    @Override
    public List<Patient> searchPatient(String searchString) {
        logger.info("Searching patient {} via JDBCTemplate", searchString);
        return jdbcTemplate.query(
                "select id, firstName, lastName, birthDate, fiscalCode from patient where lastName like :searchString or firstName like :searchString",
                Map.of("searchString", "%" + searchString + "%"),
                new BeanPropertyRowMapper<>(Patient.class)
        );
    }

    @Override
    public int countPatients() {
        logger.info("Counting patients via JDBC Template");
        return jdbcTemplate.queryForObject("select count(*) from patient", Collections.emptyMap(), Integer.class);
    }

}
