package it.intesys.academy.patient;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class PatientServiceTest {

    @Test
    public void shouldReturnPatient() {
        PatientDao patientDaoMock = Mockito.mock(PatientDao.class);
        when(patientDaoMock.findById(anyLong()))
                .thenReturn(new Patient(1L, "Mario", "Rossi", LocalDate.now().minusYears(30), "RSSMRA94R10L781Z"));

        PatientService patientService = new PatientService(patientDaoMock);
        Patient patient = patientService.getPatient(1L);
        Assert.assertNotNull(patient);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowException() {
        PatientDao patientDaoMock = Mockito.mock(PatientDao.class);
        when(patientDaoMock.findById(anyLong()))
                .thenReturn(null);

        PatientService patientService = new PatientService(patientDaoMock);
        patientService.getPatient(2L);
    }

}
