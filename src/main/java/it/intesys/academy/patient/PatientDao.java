package it.intesys.academy.patient;

import java.util.List;

public interface PatientDao {
    Patient findById(Long patientId);

    List<Patient> searchPatient(String searchString);

    int countPatients();
}
