package it.intesys.academy.patient;

import it.intesys.academy.util.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
@Primary
@Profile("rest")
public class PatientRestDao implements PatientDao {

    private static Logger logger = LoggerFactory.getLogger(PatientRestDao.class);

    private final RestTemplate restTemplate;

    public PatientRestDao(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Patient findById(Long patientId) {
        logger.info("Fetching patient {} via REST APIs", patientId);
        return restTemplate.getForObject("/patients/{patientId}", Patient.class, patientId);
    }

    @Override
    public List<Patient> searchPatient(String patientString) {
        logger.info("Searchin patient {} via REST APIs", patientString);
        Patient[] patients = restTemplate.getForObject("/patients/search?search={searchString}",
                Patient[].class, patientString);
        return Arrays.asList(patients);
    }


    @Override
    public int countPatients() {
        logger.info("Counting patients via REST APIs");
        Counter counter = restTemplate.getForObject("/patients/count", Counter.class);
        return counter.getCount();
    }
}
