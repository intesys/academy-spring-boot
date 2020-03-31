package it.intesys.academy.dashboard;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DashboardControllerIntTest {

    @Test
    @DisplayName("Dashboard home shows 0 patients")
    void testHomePage() throws Exception {
        fail("not implemented");
    }

    @Test
    @DisplayName("Dashboard home shows 3 patients counter")
    void testHomePageWithSomePatients() throws Exception {
        fail("not implemented");
    }

    @Test
    @DisplayName("Patient search page cannot be viewed by non authenticated users (status 302)")
    void testAnonymousSearchesPatients() throws Exception {
        fail("not implemented");
    }

    @Test
    @DisplayName("Patient search page can be viewed by admin user")
    void testAdminSearchesPatients() throws Exception {
        fail("not implemented");
    }
}
