package it.intesys.academy.patient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class PatientJdcbDao implements PatientDao {

    private static Logger logger = LoggerFactory.getLogger(PatientJdcbDao.class);
    private JdbcTemplate jdbcTemplate;

    public PatientJdcbDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Patient findById(Long patientId) {
        logger.info("Fetching patient {} via JDBCTemplate", patientId);
        return jdbcTemplate.queryForObject("select id, firstName, lastName, birthDate, fiscalCode from patient where id = ?",
                new Object[]{ patientId }, new BeanPropertyRowMapper<>(Patient.class));
    }

    @Override
    public List<Patient> searchPatient(String searchString) {
        logger.info("Searching patient {} via JDBCTemplate", searchString);
        String search = "%" + searchString + "%";
        return jdbcTemplate.query("select id, firstName, lastName, birthDate, fiscalCode from patient where lastName like ? or firstName like ?",
                new Object[]{searchString, search}, new BeanPropertyRowMapper<>(Patient.class));
    }

    @Override
    public int countPatients() {
        logger.info("Counting patients via JDBC Template");
        return jdbcTemplate.queryForObject("select count(*) from patient", Integer.class);
    }

}
