package it.intesys.academy.examination;

import it.intesys.academy.examination.model.Examination;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
class ExaminationService {

    private final ExaminationDao examinationDao;

    public ExaminationService(ExaminationDao examinationDao) {

        this.examinationDao = examinationDao;
    }

    public List<Examination> findByPatientId(long patientId) {

        return examinationDao.findByPatientId(patientId);
    }

    public Examination save(Examination examination) {
        examination.setExaminationDate(OffsetDateTime.now());
        return examinationDao.save(examination);
    }
}
