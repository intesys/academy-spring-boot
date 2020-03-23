package it.intesys.academy.examination.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Examination {

    @Column(name = "CREATEDATE")
    private OffsetDateTime createDate;
    @Column(name = "DIASTOLICPRESSURE")
    private int diastolicPressure;
    @Column(name = "EXAMINATIONDATE")
    private OffsetDateTime examinationDate;
    private int height;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "LASTMODIFICATIONDATE")
    private OffsetDateTime lastModificationDate;
    @Column(name = "PATIENTID")
    private int patientId;
    @Column(name = "SYSTOLICPRESSURE")
    private int systolicPressure;
    private int weight;

    public OffsetDateTime getCreateDate() {

        return createDate;
    }

    public int getDiastolicPressure() {

        return diastolicPressure;
    }

    public OffsetDateTime getExaminationDate() {

        return examinationDate;
    }

    public int getHeight() {

        return height;
    }

    public Long getId() {

        return id;
    }

    public OffsetDateTime getLastModificationDate() {

        return lastModificationDate;
    }

    public int getPatientId() {

        return patientId;
    }

    public int getSystolicPressure() {

        return systolicPressure;
    }

    public int getWeight() {

        return weight;
    }

    public void setCreateDate(OffsetDateTime createDate) {

        this.createDate = createDate;
    }

    public void setDiastolicPressure(int diastolicPressure) {

        this.diastolicPressure = diastolicPressure;
    }

    public void setExaminationDate(OffsetDateTime examinationDate) {

        this.examinationDate = examinationDate;
    }

    public void setHeight(int height) {

        this.height = height;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public void setLastModificationDate(OffsetDateTime lastModificationDate) {

        this.lastModificationDate = lastModificationDate;
    }

    public void setPatientId(int patientId) {

        this.patientId = patientId;
    }

    public void setSystolicPressure(int systolicPressure) {

        this.systolicPressure = systolicPressure;
    }

    public void setWeight(int weight) {

        this.weight = weight;
    }

    @Override
    public String toString() {

        return "Examination{" + "examinationDate=" + examinationDate + ", diastolicPressure=" + diastolicPressure + ", height=" + height + ", patientId="
                        + patientId + ", systolicPressure=" + systolicPressure + ", weight=" + weight + '}';
    }

}
