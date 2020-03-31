package it.intesys.academy.patient;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.intesys.academy.api.client.model.PatientClientDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the patient service that uses rest template instead of the database.
 * Use {@link org.springframework.test.web.client.MockRestServiceServer}
 */
public class PatientServiceApiIntTest {

    @Test
    @DisplayName("An API search with no results returns an empty list")
    void testPatientSearchWithNoResults() {
        fail("not implemented");
    }

    @Test
    @DisplayName("An API search with returns a list with 1 patient")
    void testPatientSearchWithResults() throws JsonProcessingException {
        fail("not implemented");
    }

    private PatientClientDTO newPatient(String firstName, String lastName) {
        PatientClientDTO patient = new PatientClientDTO();
        patient.setId(1L);
        patient.setFirstName(firstName);
        patient.setLastName(lastName);
        patient.setBirthDate(LocalDate.now().minusYears(30));
        patient.setFiscalCode("1234567890123456");
        return patient;
    }
}
