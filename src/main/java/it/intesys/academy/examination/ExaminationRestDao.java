package it.intesys.academy.examination;

import it.intesys.academy.api.client.api.ExaminationsApi;
import it.intesys.academy.api.client.model.ExaminationClientDTO;
import it.intesys.academy.examination.model.Examination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.sql.rowset.serial.SerialBlob;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Primary
@Profile("rest")
public class ExaminationRestDao implements ExaminationDao {

    private static Logger logger = LoggerFactory.getLogger(ExaminationRestDao.class);

    private final ExaminationsApi examinationsApi;

    public ExaminationRestDao(ExaminationsApi examinationsApi) {
        this.examinationsApi = examinationsApi;
    }

    @Override
    public List<Examination> findByPatientId(long patientId) {
        logger.info("Fetching patient {} examinations via REST APIs", patientId);
        List<ExaminationClientDTO> examinations = examinationsApi.getPatientExaminations(patientId);
        return examinations.stream()
                .map(examinationClientDTO -> toExamination(examinationClientDTO))
                .collect(Collectors.toList());
    }

    @Override
    public Examination save(Examination examination) {
        logger.info("Saving examination for patient {} via REST APIs", examination.getPatientId());
        examination.setExaminationDate(OffsetDateTime.now());
        ExaminationClientDTO examinationClientDTO = toExaminationClientDTO(examination);
        ExaminationClientDTO newExamination = examinationsApi.saveExamination(examinationClientDTO);
        return toExamination(newExamination);
    }

    private Examination toExamination(ExaminationClientDTO examinationApiDTO) {
        var examination = new Examination();
        examination.setDiastolicPressure(examinationApiDTO.getDiastolicPressure());
        examination.setExaminationDate(OffsetDateTime.ofInstant(examinationApiDTO.getExaminationDate(), ZoneId.systemDefault()));
        examination.setHeight(examinationApiDTO.getHeight());
        examination.setPatientId(Math.toIntExact(examinationApiDTO.getPatientId()));
        examination.setSystolicPressure(examinationApiDTO.getSystolicPressure());
        examination.setWeight(examinationApiDTO.getWeight());
        return examination;
    }

    private ExaminationClientDTO toExaminationClientDTO(Examination examination) {
        var examinationDTO = new ExaminationClientDTO();
        examinationDTO.setDiastolicPressure(examination.getDiastolicPressure());
        examinationDTO.setExaminationDate(examination.getExaminationDate().toInstant());
        examinationDTO.setHeight(examination.getHeight());
        examinationDTO.setPatientId((long) examination.getPatientId());
        examinationDTO.setSystolicPressure(examination.getSystolicPressure());
        examinationDTO.setWeight(examination.getWeight());
        return examinationDTO;
    }
}
