package it.intesys.academy.examination;

import it.intesys.academy.examination.model.Examination;
import it.intesys.academy.patient.Patient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class ExaminationController {

    @GetMapping(value = "/examination/new")
    public String newExamination(@RequestParam("patientId") long patientId, Model model) {

        // TODO use PatientService to get Patient by its id
        Patient patient = getMockPatient(patientId);
        model.addAttribute("patient", patient);
        return "new-examination-view";
    }

    @PostMapping("/examination/new")
    public String saveExamination(@ModelAttribute Examination examination) {

        // TODO use examinationService to save examination
        return "redirect:/patients";
    }

    private Patient getMockPatient(long id) {

        Patient patient = new Patient();
        patient.setId(id);
        patient.setFirstName("Mario");
        patient.setLastName("Rossi");
        patient.setBirthDate(LocalDate.now());
        patient.setFiscalCode("RSSMRA81S07L781N");
        return patient;
    }
}
