package it.intesys.academy.examination;

import it.intesys.academy.examination.model.Examination;
import it.intesys.academy.examination.utils.BMICalculator;
import it.intesys.academy.util.NotificationService;
import org.hibernate.Criteria;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
class ExaminationService {

    private final List<BMICalculator.BMICondition> CRITICAL_BMI_CONDITIONS = List.of(
            BMICalculator.BMICondition.EXTREMELY_OBESE,
            BMICalculator.BMICondition.UNDERWEIGHT
    );

    private final ExaminationDao examinationDao;
    private final NotificationService notificationService;

    public ExaminationService(ExaminationDao examinationDao, NotificationService notificationService) {
        this.examinationDao = examinationDao;
        this.notificationService = notificationService;
    }

    public List<Examination> findByPatientId(long patientId) {

        return examinationDao.findByPatientId(patientId);
    }

    public Examination save(Examination examination) {
        examination.setExaminationDate(OffsetDateTime.now());
        Examination newExamination = examinationDao.save(examination);
        BMICalculator.BMICondition bmiCondition =
                BMICalculator.getBMICondition(examination.getWeight(), examination.getHeight() / 100d);

        if(CRITICAL_BMI_CONDITIONS.contains(bmiCondition)) {
            notificationService.sendCriticalExaminationEvent(examination);
        }

        return newExamination;
    }
}
