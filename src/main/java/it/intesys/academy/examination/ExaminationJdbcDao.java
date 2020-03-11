package it.intesys.academy.examination;

import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.intesys.academy.examination.model.Examination;

@Repository
class ExaminationJdbcDao implements ExaminationDao {

    private static Logger logger = LoggerFactory.getLogger(ExaminationJdbcDao.class);
    private final EntityManager entityManager;

    public ExaminationJdbcDao(EntityManager entityManager) {

        this.entityManager = entityManager;
    }

    @Override
    public List<Examination> findByPatientId(long patientId) {
        logger.info("Fetching patient {} examinations via entityManager", patientId);
        TypedQuery<Examination> query =
            entityManager.createQuery("select e from Examination e where e.patientId = :patientId order by e.examinationDate", Examination.class);
        query.setParameter("patientId", Math.toIntExact(patientId));
        return query.getResultList();
    }

    @Override
    @Transactional
    public Examination save(Examination examination) {
        logger.info("Saving examination for patient {} via entityManager", examination.getPatientId());
        examination.setExaminationDate(OffsetDateTime.now());
        entityManager.persist(examination);
        return examination;
    }
}


