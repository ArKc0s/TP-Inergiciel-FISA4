package fr.intergiciel.fetchtreat.tables;


import java.sql.Date;

public class Stay {
    private String stayId;
    private Date startDate;
    private Date endDate;
    private Patient patient;

    public Stay(String stayId, Date startDate, Date endDate, Patient patient) {
        this.stayId = stayId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.patient = patient;
    }

    public String getStayId() {
        return stayId;
    }

    public void setStayId(String stayId) {
        this.stayId = stayId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "Stay{" +
                "stayId='" + stayId + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", patient=" + patient +
                '}';
    }
}
