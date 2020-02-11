package it.intesys.academy.examination;

import it.intesys.academy.examination.model.Examination;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExaminationService {

    private final ExaminationDao examinationDao;

    public ExaminationService(ExaminationDao examinationDao) {

        this.examinationDao = examinationDao;
    }

    public List<Examination> findByPatientId(long patientId) {

        return examinationDao.findByPatientId(patientId);
    }

    public void save(Examination examination) {

        examinationDao.save(examination);
    }
}
