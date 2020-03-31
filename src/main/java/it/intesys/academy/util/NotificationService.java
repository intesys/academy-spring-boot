package it.intesys.academy.util;

import it.intesys.academy.examination.model.Examination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public void sendCriticalExaminationEvent(Examination examination) {
        logger.info("Sending notification for critical examination {}", examination.getId());
    }
}
