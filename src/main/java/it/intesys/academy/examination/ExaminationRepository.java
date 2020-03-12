package it.intesys.academy.examination;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.intesys.academy.examination.model.Examination;

public interface ExaminationRepository extends JpaRepository<Examination, Long> {

    @Query("select e from Examination e where e.patientId = :patientId order by e.examinationDate")
    List<Examination> findByPatientId(int patientId);
}
