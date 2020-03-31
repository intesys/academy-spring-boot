package it.intesys.academy.patient;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

public class PatientServiceTest {

    private PatientDao patientDao;

    private PatientService patientService;

    @Test
    @DisplayName("Returns a patient by id")
    public void shouldReturnPatient() {
        fail("Not Implemented");
    }

    @Test
    @DisplayName("Throws exception beacause patient not found")
    public void shouldThrowException() {
        fail("Not Implemented");
    }

    private Patient newPatient(Long id, String firstName, String lastName) {
        return new Patient(id, firstName, lastName, LocalDate.now().minusYears(39), "01234567890123456");
    }

}
