package it.intesys.academy.patient;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("select p from Patient p where p.lastName like :searchString or p.firstName like :searchString")
    List<Patient> searchPatient(String searchString);
}
