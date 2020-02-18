package it.intesys.academy.patient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PatientRestController {

    private Logger logger = LoggerFactory.getLogger(PatientRestController.class);

    private final PatientService patientService;

    public PatientRestController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/patients/search")
    public List<Patient> searchPatient(@RequestParam("search") String search ) {
        logger.info("Searching for {}", search);
        return patientService.searchPatient(search);
    }
}
