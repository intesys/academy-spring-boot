package it.intesys.academy.patient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.intesys.academy.api.client.model.PatientClientDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * Tests the patient service that uses rest template instead of the database.
 * Use {@link org.springframework.test.web.client.MockRestServiceServer}
 */
@SpringBootTest
@ActiveProfiles("rest")
public class PatientServiceApiIntTest {

    @Autowired
    PatientService patientService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RestTemplate restTemplate;

    MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    @DisplayName("An API search with no results returns an empty list")
    void testPatientSearchWithNoResults() {
        //given
        mockServer.expect(ExpectedCount.once(),
                requestTo("http://localhost:8080/api/patients/search?search=Mari"))
                .andRespond(withSuccess("[]", MediaType.APPLICATION_JSON));

        //test
        List<Patient> patients = patientService.searchPatient("Mari");

        //assert
        mockServer.verify();
        assertThat(patients).hasSize(0);
    }

    @Test
    @DisplayName("An API search with returns a list with 1 patient")
    void testPatientSearchWithResults() throws JsonProcessingException {
        //given
        var patientList = List.of(newPatient("Giuseppe", "Verdi"));
        String patientListResponse = objectMapper.writeValueAsString(patientList);
        mockServer.expect(ExpectedCount.once(),
                requestTo("http://localhost:8080/api/patients/search?search=Verdi"))
                .andRespond(withSuccess(patientListResponse, MediaType.APPLICATION_JSON));

        //test
        List<Patient> patients = patientService.searchPatient("Verdi");

        //assert
        mockServer.verify();
        assertThat(patients).hasSize(1);
        assertThat(patients).extracting("lastName").containsOnly("Verdi");
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
