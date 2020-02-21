package it.intesys.academy.examination;

import it.intesys.academy.examination.model.Examination;

import java.util.List;

public interface ExaminationDao {

    List<Examination> findByPatientId(long patientId);

    Examination save(Examination examination);
}
