package it.intesys.academy.aop;

import java.time.OffsetDateTime;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import it.intesys.academy.examination.ExaminationRepository;
import it.intesys.academy.examination.model.Examination;

@Component
@Aspect
public class AuditAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditAspect.class);
    private final ExaminationRepository examinationRepository;

    public AuditAspect(ExaminationRepository examinationRepository) {

        this.examinationRepository = examinationRepository;
    }

    @Pointcut("execution(public * it.intesys.academy.examination.ExaminationJdbcDao.save(..))")
    public void dao() {

    }

    @AfterReturning(pointcut = "dao()", returning = "examination")
    public void saveAuditData(Examination examination) {
        LOGGER.warn("saving audit data for " + examination);
        if (examination.getCreateDate() == null) {
            examination.setCreateDate(OffsetDateTime.now());
        }
        examination.setLastModificationDate(OffsetDateTime.now());
        examinationRepository.save(examination);
    }
}
