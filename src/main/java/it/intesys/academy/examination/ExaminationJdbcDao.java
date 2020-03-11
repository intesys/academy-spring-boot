package it.intesys.academy.examination;

import java.time.OffsetDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.intesys.academy.examination.model.Examination;

@Repository
class ExaminationJdbcDao implements ExaminationDao {

    private static Logger logger = LoggerFactory.getLogger(ExaminationJdbcDao.class);
    private ExaminationRepository examinationRepository;

    public ExaminationJdbcDao(ExaminationRepository examinationRepository) {

        this.examinationRepository = examinationRepository;
    }

    @Override
    public List<Examination> findByPatientId(long patientId) {

        logger.info("Fetching patient {} examinations via JpaRepository", patientId);

        return examinationRepository.findByPatientIdOrderByExaminationDate(Math.toIntExact(patientId));
    }

    @Override
    @Transactional
    public Examination save(Examination examination) {

        logger.info("Saving examination for patient {} via JpaRepository", examination.getPatientId());
        examination.setExaminationDate(OffsetDateTime.now());

        return examinationRepository.save(examination);
    }
}
