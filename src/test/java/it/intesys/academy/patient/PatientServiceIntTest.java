package it.intesys.academy.patient;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class PatientServiceIntTest {

    @Autowired
    PatientService patientService;

    @Autowired
    PatientRepository patientRepository;

    @Test
    @DisplayName("Case sensitive search works with patient in the database")
    void testPatientSearch() {
        //given
        patientRepository.saveAll(List.of(
                newPatient("Mario", "Rossi"),
                newPatient("Maria", "Bianchi"),
                newPatient("giuseppe", "verdi")
        ));

        //test
        List<Patient> patients = patientService.searchPatient("Mari");

        //assert
        assertThat(patients).hasSize(2);
        assertThat(patients).extracting("lastName").containsOnly("Rossi", "Bianchi");
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
