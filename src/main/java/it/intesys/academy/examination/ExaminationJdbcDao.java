package it.intesys.academy.examination;

import it.intesys.academy.examination.model.Examination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
class ExaminationJdbcDao implements ExaminationDao {

    private static Logger logger = LoggerFactory.getLogger(ExaminationJdbcDao.class);
    private final JdbcTemplate jdbcTemplate;

    public ExaminationJdbcDao(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Examination> findByPatientId(long patientId) {
        logger.info("Fetching patient {} examinations via JDBC template", patientId);
        return jdbcTemplate
            .query("SELECT * FROM examination WHERE patientId = ? ORDER BY examinationDate", new BeanPropertyRowMapper<>(Examination.class), patientId);
    }

    @Override
    public void save(Examination examination) {
        logger.info("Saving examination for patient {} via JDBC Template", examination.getPatientId());
        jdbcTemplate
            .update("INSERT INTO examination(patientId, diastolicPressure, systolicPressure, height, weight, examinationDate) VALUES (?, ?, ?, ?, ?, ?)",
                    examination.getPatientId(), examination.getDiastolicPressure(), examination.getSystolicPressure(), examination.getHeight(),
                    examination.getWeight(), OffsetDateTime.now());
    }
}


