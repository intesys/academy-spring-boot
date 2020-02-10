package it.intesys.academy.patient;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;

@Controller
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {

        this.patientService = patientService;
    }

    @GetMapping(value = "/patients")
    public String patientList() {

        return "patient-view";
    }

    @PostMapping(value = "/patient/search")
    public String searchPatient(@RequestParam String search, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("patients", StringUtils.isEmpty(search) ? Collections.emptyList() : patientService.searchPatient(search));
        redirectAttributes.addFlashAttribute("search", search);
        return "redirect:/patients";
    }
}
