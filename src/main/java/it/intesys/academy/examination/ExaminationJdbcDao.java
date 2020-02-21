package it.intesys.academy.examination;

import it.intesys.academy.examination.model.Examination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
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
    public Examination save(Examination examination) {
        logger.info("Saving examination for patient {} via JDBC Template", examination.getPatientId());
        String inserExaminationSQL =
                "INSERT INTO examination(patientId, diastolicPressure, systolicPressure, height, weight, examinationDate) VALUES (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(inserExaminationSQL, new String[]{"id"});
            ps.setLong(1, examination.getPatientId());
            ps.setInt(2, examination.getDiastolicPressure());
            ps.setInt(3, examination.getSystolicPressure());
            ps.setInt(4, examination.getHeight());
            ps.setInt(5, examination.getWeight());
            ps.setDate(6, new Date(Date.from(examination.getExaminationDate().toInstant()).getTime()));
            return ps;
        }, keyHolder);

        examination.setId(keyHolder.getKey().longValue());

        return examination;

    }
}


