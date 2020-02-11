package it.intesys.academy.patient;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class Patient {

    private LocalDate birthDate;
    private String firstName;
    private String fiscalCode;
    private Long id;
    private String lastName;

    public Patient(Long id, String firstName, String lastName, LocalDate birthDate, String fiscalCode) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.fiscalCode = fiscalCode;
    }

    public Patient() {

    }

    public LocalDate getBirthDate() {

        return birthDate;
    }

    public String getFirstName() {

        return firstName;
    }

    public String getFiscalCode() {

        return fiscalCode;
    }

    public Long getId() {

        return id;
    }

    public String getLastName() {

        return lastName;
    }

    public void setBirthDate(LocalDate birthDate) {

        this.birthDate = birthDate;
    }

    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    public void setFiscalCode(String fiscalCode) {

        this.fiscalCode = fiscalCode;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public void setLastName(String lastName) {

        this.lastName = lastName;
    }

    @Override
    public String toString() {

        return "Patient{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", birthDate=" + birthDate + ", fiscalCode='" +
               fiscalCode + '\'' + '}';
    }

}
