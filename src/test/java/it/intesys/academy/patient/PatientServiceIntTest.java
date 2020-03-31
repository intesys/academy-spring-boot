package it.intesys.academy.patient;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

public class PatientServiceIntTest {

    @Test
    @DisplayName("Case sensitive search works with patient in the database")
    void testPatientSearch() {
        fail("not implemented");
    }

    private Patient newPatient(String firstName, String lastName) {
        Patient patient = new Patient();
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setBirthDate(LocalDate.now().minusYears(30));
        patient.setFiscalCode("1234567890123456");
        return patient;
    }
}
