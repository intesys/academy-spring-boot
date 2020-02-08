package it.intesys.academy;

import it.intesys.academy.patient.Patient;
import it.intesys.academy.patient.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PatientSearch implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger("PatientSearch");

    private final PatientService patientService;

    public PatientSearch(PatientService patientService) {
        this.patientService = patientService;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Patient> patients = patientService.searchPatient(args[0]);
        logger.info("\nFound {} patients!", patients.size());
        for (Patient patient : patients) {
            logger.info("\t{}", patient);
        }
    }
}
