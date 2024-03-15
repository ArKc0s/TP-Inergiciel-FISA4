package fr.intergiciel.fetchtreat.tables;

public class Patient {
    private String patientId;
    private String birthName;
    private String legalName;
    private String firstName;
    private String prefix;
    private java.sql.Date birthDate;

    public Patient(String patientId, String birthName, String legalName, String firstName, String prefix, java.sql.Date birthDate) {
        this.patientId = patientId;
        this.birthName = birthName;
        this.legalName = legalName;
        this.firstName = firstName;
        this.prefix = prefix;
        this.birthDate = birthDate;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getBirthName() {
        return birthName;
    }

    public void setBirthName(String birthName) {
        this.birthName = birthName;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public java.sql.Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(java.sql.Date birthDate) {
        this.birthDate = birthDate;
    }
}
