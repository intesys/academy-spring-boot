package it.intesys.academy.patient;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("rest")
@Testcontainers
public class PatientServiceApiTestContainerIT {

    @Container
    public static GenericContainer restServer = new GenericContainer("rodolpheche/wiremock")
            .withClasspathResourceMapping("wiremock/stubs", "/home/wiremock", BindMode.READ_WRITE)
            .withExposedPorts(8080);

    @DynamicPropertySource
    static void addProperty(DynamicPropertyRegistry registry) {
        registry.add(
                "application.api-endpoint",
                ()-> "http://localhost:" + restServer.getMappedPort(8080)+ "/api"
        );
    }

    @Autowired
    PatientService patientService;

    @Test
    @DisplayName("An API search with no results returns an empty list")
    void testPatientSearchWithNoResults() {
        List<Patient> patients = patientService.searchPatient("NoPatient");
        assertThat(patients).hasSize(0);
    }

    @Test
    @DisplayName("An API search with returns a list with 1 patient")
    void testPatientSearchWithResults() throws JsonProcessingException {
        List<Patient> patients = patientService.searchPatient("Verdi");
        assertThat(patients).hasSize(1);
        assertThat(patients).extracting("lastName").containsOnly("Verdi");
    }

}
