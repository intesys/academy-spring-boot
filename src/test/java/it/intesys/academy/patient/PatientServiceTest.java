package it.intesys.academy.patient;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PatientServiceTest {

    private PatientDao patientDao;

    private PatientService patientService;

    @BeforeEach
    void setUp() {
        patientDao = Mockito.mock(PatientDao.class);
        patientService = new PatientService(patientDao);
    }

    @Test
    @DisplayName("Returns a patient by id")
    public void shouldReturnPatient() {

        //given
        when(patientDao.findById(1L))
                .thenReturn(newPatient(1L, "Mario", "Rossi"));

        //test
        Patient patient = patientService.getPatient(1L);

        //assert
        assertThat(patient).extracting("firstName").isEqualTo("Mario");
        assertThat(patient).extracting("lastName").isEqualTo("Rossi");
    }

    @Test
    @DisplayName("Throws exception beacause patient not found")
    public void shouldThrowException() {
        //given
        when(patientDao.findById(1L))
                .thenReturn(null);

        // test & assert
        Assertions.assertThrows(RuntimeException.class, ()-> patientService.getPatient(1L));
    }

    private Patient newPatient(Long id, String firstName, String lastName) {
        return new Patient(id, firstName, lastName, LocalDate.now().minusYears(39), "01234567890123456");
    }

}
