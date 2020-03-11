package it.intesys.academy.patient;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
class PatientJdcbDao implements PatientDao {

    private static Logger logger = LoggerFactory.getLogger(PatientJdcbDao.class);
    private final PatientRepository patientRepository;

    public PatientJdcbDao(PatientRepository patientRepository) {

        this.patientRepository = patientRepository;
    }

    @Override
    public int countPatients() {

        logger.info("Counting patients via JpaRepository");
        return Math.toIntExact(patientRepository.count());
    }

    @Override
    public Patient findById(Long patientId) {

        logger.info("Fetching patient {} via JpaRepository", patientId);
        return patientRepository.getOne(patientId);
    }

    @Override
    public List<Patient> searchPatient(String searchString) {

        logger.info("Searching patient {} via JpaRepository", searchString);
        return patientRepository.findByLastNameContainingOrFirstNameContaining(searchString, searchString);
    }
}
