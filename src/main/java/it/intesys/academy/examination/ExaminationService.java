package it.intesys.academy.examination;

import it.intesys.academy.examination.model.Examination;
import org.springframework.stereotype.Service;

@Service
public class ExaminationService {

    private final ExaminationDao examinationDao;

    public ExaminationService(ExaminationDao examinationDao) {

        this.examinationDao = examinationDao;
    }

    public void save(Examination examination) {

        examinationDao.save(examination);
    }
}
