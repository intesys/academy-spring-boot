package it.intesys.academy.patient;

import it.intesys.academy.api.client.api.PatientApi;
import it.intesys.academy.api.client.model.CounterClientDTO;
import it.intesys.academy.api.client.model.PatientClientDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Primary
@Profile("rest")
public class PatientRestDao implements PatientDao {

    private static Logger logger = LoggerFactory.getLogger(PatientRestDao.class);

    private final PatientApi patientApi;

    public PatientRestDao(PatientApi patientApi) {
        this.patientApi = patientApi;
    }

    @Override
    public Patient findById(Long patientId) {
        logger.info("Fetching patient {} via REST APIs", patientId);
        PatientClientDTO patientDTO = patientApi.getPatient(patientId);
        return toPatient(patientDTO);
    }

    @Override
    public List<Patient> searchPatient(String patientString) {
        logger.info("Searchin patient {} via REST APIs", patientString);
        List<PatientClientDTO> patients = patientApi.searchPatient(patientString);
        return patients.stream()
                .map(patientClientDTO -> toPatient(patientClientDTO))
                .collect(Collectors.toList());
    }

    @Override
    public int countPatients() {
        CounterClientDTO counter = patientApi.countPatients();
        logger.info("Counting patients via REST APIs");
        return counter.getCount();
    }

    private Patient toPatient(PatientClientDTO patientClientDTO) {
        Patient patient = new Patient();
        patient.setId(patientClientDTO.getId());
        patient.setFirstName(patientClientDTO.getFirstName());
        patient.setLastName(patientClientDTO.getLastName());
        patient.setBirthDate(patientClientDTO.getBirthDate());
        patient.setFiscalCode(patientClientDTO.getFiscalCode());
        return patient;
    }
}
