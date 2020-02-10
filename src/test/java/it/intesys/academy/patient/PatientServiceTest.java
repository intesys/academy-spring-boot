package it.intesys.academy.patient;

import org.junit.Assert;
import org.junit.Test;

public class PatientServiceTest {

    @Test
    public void shouldReturnPatient() {
        PatientService patientService = new PatientService();
        Patient patient = patientService.getPatient(1L);
        Assert.assertNotNull(patient);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowException() {
        PatientService patientService = new PatientService();
        patientService.getPatient(2L);
    }

}
