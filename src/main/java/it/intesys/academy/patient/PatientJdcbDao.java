package it.intesys.academy.patient;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
class PatientJdcbDao implements PatientDao {

    private static Logger logger = LoggerFactory.getLogger(PatientJdcbDao.class);
    private final EntityManager entityManager;

    public PatientJdcbDao(EntityManager entityManager) {

        this.entityManager = entityManager;
    }

    @Override
    public int countPatients() {

        logger.info("Counting patients via entityManager");

        TypedQuery<Long> query = entityManager.createQuery("select count(p) from Patient p", Long.class);
        return Math.toIntExact(query.getSingleResult());
    }

    @Override
    public Patient findById(Long patientId) {

        logger.info("Fetching patient {} via entityManager", patientId);

        TypedQuery<Patient> query = entityManager.createQuery("select p from Patient p where p.id = :id", Patient.class);
        query.setParameter("id", patientId);
        return query.getSingleResult();
    }

    @Override
    public List<Patient> searchPatient(String searchString) {

        logger.info("Searching patient {} via entityManager", searchString);
        TypedQuery<Patient> query = entityManager.createQuery("select p from Patient p where p.lastName like :searchString or p.firstName like :searchString",
                        Patient.class);
        query.setParameter("searchString", "%" + searchString + "%");
        return query.getResultList();
    }

}
