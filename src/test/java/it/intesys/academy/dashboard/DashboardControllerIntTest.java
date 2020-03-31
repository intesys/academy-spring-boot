package it.intesys.academy.dashboard;

import it.intesys.academy.patient.PatientController;
import it.intesys.academy.patient.PatientService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {DashboardController.class, PatientController.class})
@AutoConfigureMockMvc
class DashboardControllerIntTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PatientService patientService;

    @Test
    @DisplayName("Dashboard home shows 0 patients")
    void testHomePage() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("0 Patients")));
    }

    @Test
    @DisplayName("Dashboard home shows 3 patients counter")
    void testHomePageWithSomePatients() throws Exception {
        when(patientService.countPatients()).thenReturn(3);
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("3 Patients")));
    }

    @Test
    @DisplayName("Patient search page cannot be viewed by non authenticated users (status 302)")
    void testAnonymousSearchesPatients() throws Exception {
        mockMvc.perform(get("/patients"))
                .andExpect(status().is(302));
    }

    @Test
    @DisplayName("Patient search page can be viewed by admin user")
    @WithMockUser(value = "admin", authorities = "ADMIN")
    void testAdminSearchesPatients() throws Exception {
        mockMvc.perform(get("/patients"))
                .andExpect(status().is(200));
    }
}
