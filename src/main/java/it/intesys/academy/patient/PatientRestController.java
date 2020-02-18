package it.intesys.academy.patient;

import it.intesys.academy.util.Counter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/patients/{patientId}")
    public Patient getPatient(@PathVariable("patientId") Long patientId) {
        logger.info("Retrieving patient with id {}", patientId);
        return patientService.getPatient(patientId);
    }

    @GetMapping("/patients/count")
    public Counter countPatients() {
        return new Counter(patientService.countPatients());
    }
}
