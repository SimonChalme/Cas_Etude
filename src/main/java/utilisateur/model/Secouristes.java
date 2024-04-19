package utilisateur.model;

public class Secouristes extends User {
    private Boolean occupé;
    String position;

    private int idIntervention;

    public Secouristes(int id, String nom, String prenom, Boolean occupé, String position, int idIntervention) {
        super(id, nom, prenom);
        this.occupé = occupé;
        this.position = position;
        this.idIntervention = idIntervention;
    }

    public Boolean getOccupé() {
        return occupé;
    }

    public void setOccupé(Boolean occupé) {
        this.occupé = occupé;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getIdIntervention() {
        return idIntervention;
    }

    public void setIdIntervention(int idIntervention) {
        this.idIntervention = idIntervention;
    }
}
