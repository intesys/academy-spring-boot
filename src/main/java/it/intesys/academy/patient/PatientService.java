package it.intesys.academy.patient;

import java.util.List;

public class PatientService {

    public Patient getPatient(Long patientId) {
        Patient patient = new PatientDao().findById(patientId);

        if(patient == null) {
            throw new RuntimeException("Patient not found");
        }

        return patient;
    }

    public List<Patient> searchPatient(String patientString) {
        List<Patient> patients = new PatientDao().searchPatient(patientString);

        if(patients == null) {
            throw new RuntimeException("No patients found for search string" + patientString);
        }

        return patients;
    }
}
