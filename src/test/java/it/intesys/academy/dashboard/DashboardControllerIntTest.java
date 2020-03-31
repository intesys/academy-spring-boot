package it.intesys.academy.dashboard;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class DashboardControllerIntTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Dashboard home shows 0 patients")
    void testHomePage() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("0 Patients")));
    }

    @Test
    @DisplayName("Dashboard home shows 3 patients counter")
    @Sql(statements = {
            "INSERT INTO patient (firstName, lastName, birthDate, fiscalCode) VALUES ('Enrico', 'Costanzi', '1988-01-01', 'CSTNRC88A01L781U')",
            "INSERT INTO patient (firstName, lastName, birthDate, fiscalCode) VALUES ('Tommaso', 'Moroni', '1981-11-07', 'MRNTMS81S07L781N')",
            "INSERT INTO patient (firstName, lastName, birthDate, fiscalCode) VALUES ('Nicola', 'Baiocco', '1985-06-03', 'BCCNCL85H03L781Q')"
    })
    void testHomePageWithSomePatients() throws Exception {
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
