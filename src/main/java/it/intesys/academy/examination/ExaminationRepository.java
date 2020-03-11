package it.intesys.academy.examination;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.intesys.academy.examination.model.Examination;

public interface ExaminationRepository extends JpaRepository<Examination, Long> {

    List<Examination> findByPatientIdOrderByExaminationDate(int patientId);
}
