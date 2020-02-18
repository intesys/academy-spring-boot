package it.intesys.academy.examination;

import it.intesys.academy.examination.model.Examination;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExaminationRestController {

    private final ExaminationService examinationService;

    public ExaminationRestController(ExaminationService examinationService) {
        this.examinationService = examinationService;
    }

    @GetMapping("/patients/{patientId}/examinations")
    public List<Examination> patientExaminations(@PathVariable long patientId) {
        return examinationService.findByPatientId(patientId);
    }

    @PostMapping("/examinations")
    @ResponseStatus(HttpStatus.CREATED)
    public Examination saveExamination(@RequestBody Examination examination) {
        examinationService.save(examination);
        return examination;
    }
}
