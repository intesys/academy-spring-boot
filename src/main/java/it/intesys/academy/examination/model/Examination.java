package it.intesys.academy.examination.model;

import java.time.OffsetDateTime;

public class Examination {

    private Long id;
    private int diastolicPressure;
    private OffsetDateTime examinationDate;
    private int height;
    private int patientId;
    private int systolicPressure;
    private int weight;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getPatientId() {

        return patientId;
    }

    public int getSystolicPressure() {

        return systolicPressure;
    }

    public int getWeight() {

        return weight;
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

        return "Examination{" + "examinationDate=" + examinationDate + ", diastolicPressure=" + diastolicPressure + ", height=" + height + ", patientId=" +
               patientId + ", systolicPressure=" + systolicPressure + ", weight=" + weight + '}';
    }

}
