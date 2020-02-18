package it.intesys.academy.patient;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private PatientDao patientDao;

    public PatientService(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    public Patient getPatient(Long patientId) {
        Patient patient = patientDao.findById(patientId);

        if(patient == null) {
            throw new RuntimeException("Patient not found");
        }

        return patient;
    }

    public List<Patient> searchPatient(String patientString) {
        List<Patient> patients = patientDao.searchPatient(patientString);

        if(patients == null) {
            throw new RuntimeException("No patients found for search string" + patientString);
        }

        return patients;
    }

    public int countPatients() {
        return patientDao.countPatients();
    }
}
