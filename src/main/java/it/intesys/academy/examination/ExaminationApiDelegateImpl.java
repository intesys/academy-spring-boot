package it.intesys.academy.examination;

import it.intesys.academy.examination.model.Examination;
import it.intesys.academy.web.api.ExaminationsApiDelegate;
import it.intesys.academy.web.api.model.ExaminationApiDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExaminationApiDelegateImpl implements ExaminationsApiDelegate {

    private final ExaminationService examinationService;

    public ExaminationApiDelegateImpl(ExaminationService examinationService) {
        this.examinationService = examinationService;
    }

    @Override
    public ResponseEntity<List<ExaminationApiDTO>> getPatientExaminations(Long patientId) {
        List<Examination> examinations = examinationService.findByPatientId(patientId);
        List<ExaminationApiDTO> examinationDTOs = examinations.stream()
                .map(examination -> toExaminationApiDTO(examination))
                .collect(Collectors.toList());
        return ResponseEntity.ok(examinationDTOs);
    }

    @Override
    public ResponseEntity<ExaminationApiDTO> saveExamination(ExaminationApiDTO examinationApiDTO) {
        examinationService.save(toExamination(examinationApiDTO));
        return new ResponseEntity<>(examinationApiDTO, HttpStatus.CREATED);
    }

    private ExaminationApiDTO toExaminationApiDTO(Examination examination) {
        return new ExaminationApiDTO()
                .diastolicPressure(examination.getDiastolicPressure())
                .examinationDate(examination.getExaminationDate().toInstant())
                .height(examination.getHeight())
                .weight(examination.getWeight())
                .systolicPressure(examination.getSystolicPressure())
                .patientId((long) examination.getPatientId());

    }

    private Examination toExamination(ExaminationApiDTO examinationApiDTO) {
        Examination examination = new Examination();
        examination.setDiastolicPressure(examinationApiDTO.getDiastolicPressure());
        examination.setExaminationDate(OffsetDateTime.ofInstant(examinationApiDTO.getExaminationDate(), ZoneId.systemDefault()));
        examination.setHeight(examinationApiDTO.getHeight());
        examination.setWeight(examinationApiDTO.getWeight());
        examination.setPatientId(Math.toIntExact(examinationApiDTO.getPatientId()));;
        examination.setSystolicPressure(examinationApiDTO.getSystolicPressure());
        return examination;
    }
}
