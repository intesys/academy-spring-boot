package it.intesys.academy.patient;

import it.intesys.academy.web.api.PatientApiDelegate;
import it.intesys.academy.web.api.model.CounterApiDTO;
import it.intesys.academy.web.api.model.PatientApiDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientApiDelegateImpl implements PatientApiDelegate {

    private final PatientService patientService;

    public PatientApiDelegateImpl(PatientService patientService) {
        this.patientService = patientService;
    }

    @Override
    public ResponseEntity<CounterApiDTO> countPatients() {
        CounterApiDTO counter = new CounterApiDTO().count(patientService.countPatients());
        return ResponseEntity.ok(counter);
    }

    @Override
    public ResponseEntity<PatientApiDTO> getPatient(Long patientId) {
        Patient patient = patientService.getPatient(patientId);
        return ResponseEntity.ok(toPatientApiDTO(patient));
    }

    @Override
    public ResponseEntity<List<PatientApiDTO>> searchPatient(String search) {
        List<Patient> patients = patientService.searchPatient(search);
        var patientDTOList = patients.stream()
                .map(this::toPatientApiDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(patientDTOList);
    }

    private PatientApiDTO toPatientApiDTO(Patient patient) {
        return new PatientApiDTO()
                .id(patient.getId())
                .birthDate(patient.getBirthDate())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .fiscalCode(patient.getFiscalCode());

    }
}
