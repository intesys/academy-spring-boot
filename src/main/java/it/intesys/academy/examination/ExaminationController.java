package it.intesys.academy.examination;

import it.intesys.academy.examination.model.Examination;
import it.intesys.academy.patient.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ExaminationController {

    private final ExaminationService examinationService;
    private final PatientService patientService;

    public ExaminationController(ExaminationService examinationService, PatientService patientService) {

        this.examinationService = examinationService;
        this.patientService = patientService;
    }

    @GetMapping(value = "/examination/new")
    public String newExamination(@RequestParam("patientId") long patientId, Model model) {

        model.addAttribute("patient", patientService.getPatient(patientId));
        return "new-examination-view";
    }

    @PostMapping("/examination/new")
    public String saveExamination(@ModelAttribute Examination examination) {

        examinationService.save(examination);
        return "redirect:/patients";
    }
}
