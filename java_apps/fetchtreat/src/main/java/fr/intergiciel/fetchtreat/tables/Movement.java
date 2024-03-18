package fr.intergiciel.fetchtreat.tables;


public class Movement {
    private int movementId;
    private String service;
    private String room;
    private String bed;
    private Stay stay;
    private Patient patient;

    public Movement(int movementId, String service, String room, String bed, Stay stay, Patient patient) {
        this.movementId = movementId;
        this.service = service;
        this.room = room;
        this.bed = bed;
        this.stay = stay;
        this.patient = patient;
    }

    public int getMovementId() {
        return movementId;
    }

    public void setMovementId(int movementId) {
        this.movementId = movementId;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public Stay getStay() {
        return stay;
    }

    public void setStay(Stay stay) {
        this.stay = stay;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "Movement{" +
                "movementId=" + movementId +
                ", service='" + service + '\'' +
                ", room='" + room + '\'' +
                ", bed='" + bed + '\'' +
                ", stay=" + stay +
                ", patient=" + patient +
                '}';
    }
}
