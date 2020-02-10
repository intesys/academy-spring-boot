package it.intesys.academy.dashboard;

import it.intesys.academy.patient.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final PatientService patientService;

    public DashboardController(PatientService patientService) {

        this.patientService = patientService;
    }

    @GetMapping
    public String home(Model model) {

        model.addAttribute("patientsCount", patientService.countPatients());
        return "index";
    }
}
