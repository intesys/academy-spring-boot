package it.intesys.academy.examination;

import it.intesys.academy.examination.model.Examination;
import it.intesys.academy.util.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExaminationServiceTest {

    @Mock
    ExaminationDao examinationDao;

    @Spy
    NotificationService notificationService;

    private ExaminationService examinationService;

    @BeforeEach
    void setUp() {
        examinationService = new ExaminationService(examinationDao, notificationService);
    }

    @DisplayName("send notification when patient examination BMI is critical")
    @Test
    public void testBmiNotification() {
        //given
        Examination examination = new Examination();
        examination.setHeight(195);
        examination.setWeight(200);

        //test
        examinationService.save(examination);

        //verify
        verify(notificationService, times(1)).sendCriticalExaminationEvent(examination);
    }

    @DisplayName("Dont send notification when patient examination BMI is ok")
    @Test
    public void testBmiNotificationNotSent() {
        //given
        Examination examination = new Examination();
        examination.setHeight(195);
        examination.setWeight(85);

        //test
        examinationService.save(examination);

        //verify
        verify(notificationService, times(0)).sendCriticalExaminationEvent(examination);
    }

}
