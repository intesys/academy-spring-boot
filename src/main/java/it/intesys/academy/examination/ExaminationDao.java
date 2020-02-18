package it.intesys.academy.examination;

import it.intesys.academy.examination.model.Examination;

import java.util.List;

public interface ExaminationDao {

    List<Examination> findByPatientId(long patientId);

    void save(Examination examination);
}
