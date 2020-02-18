package it.intesys.academy.examination;

import it.intesys.academy.examination.model.Examination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.sql.rowset.serial.SerialBlob;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@Primary
@Profile("rest")
public class ExaminationRestDao implements ExaminationDao {

    private static Logger logger = LoggerFactory.getLogger(ExaminationRestDao.class);

    private final RestTemplate restTemplate;

    public ExaminationRestDao(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Examination> findByPatientId(long patientId) {
        logger.info("Fetching patient {} examinations via REST APIs", patientId);
        Examination[] examinations = restTemplate.getForObject("/patients/{patientId}/examinations", Examination[].class, patientId);
        return Arrays.asList(examinations);
    }

    @Override
    public void save(Examination examination) {
        logger.info("Saving examination for patient {} via REST APIs", examination.getPatientId());
        examination.setExaminationDate(OffsetDateTime.now());
        restTemplate.postForEntity("/examinations", examination, Void.class);
    }
}
