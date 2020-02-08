package it.intesys.academy;

import it.intesys.academy.patient.Patient;
import it.intesys.academy.patient.PatientDao;
import it.intesys.academy.patient.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SimpleApp {

    private static Logger logger = LoggerFactory.getLogger(SimpleApp.class);

    public static void main(String[] args) {

        // app entry point, will become a REST controller
        String searchString = args[0];
        logger.info("Searching for {}", searchString);
        List<Patient> patients = new PatientService(new PatientDao()).searchPatient(searchString);
        logger.info("\nFound {} patients!", patients.size());

        for (Patient patient : patients) {
            logger.info("\t{}", patient);
        }

    }
}
