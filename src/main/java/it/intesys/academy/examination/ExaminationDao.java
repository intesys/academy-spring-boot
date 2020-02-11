package it.intesys.academy.examination;

import it.intesys.academy.examination.model.Examination;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Component
public class ExaminationDao {

    private final JdbcTemplate jdbcTemplate;

    public ExaminationDao(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Examination> findByPatientId(long patientId) {

        return jdbcTemplate
            .query("SELECT * FROM examination WHERE patientId = ? ORDER BY examinationDate", new BeanPropertyRowMapper<>(Examination.class), patientId);
    }

    public void save(Examination examination) {

        jdbcTemplate
            .update("INSERT INTO examination(patientId, diastolicPressure, systolicPressure, height, weight, examinationDate) VALUES (?, ?, ?, ?, ?, ?)",
                    examination.getPatientId(), examination.getDiastolicPressure(), examination.getSystolicPressure(), examination.getHeight(),
                    examination.getWeight(), OffsetDateTime.now());
    }
}


