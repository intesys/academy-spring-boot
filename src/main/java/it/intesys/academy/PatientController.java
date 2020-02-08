package it.intesys.academy;

import it.intesys.academy.patient.Patient;
import it.intesys.academy.patient.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PatientController {

    private Logger logger = LoggerFactory.getLogger(PatientController.class);

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patient")
    public List<Patient> searchPatient(@RequestParam("search") String search ) {
        logger.info("Searching for {}", search);
        return patientService.searchPatient(search);
    }
}
