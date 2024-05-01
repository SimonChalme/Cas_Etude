package utilisateur.model;

import java.util.ArrayList;

public class PersonelDeSoin extends User{
    private Boolean occupé;
    private ArrayList<Patient> patients;

    public PersonelDeSoin(int id, String nom, String prenom, Boolean occupé, ArrayList<Patient> patients) {
        super(id, nom, prenom);
        this.occupé = occupé;
        // ici aussi j'imagine que l'on doit initialiser avec la table de données
        this.patients = patients;
    }

    public Boolean getOccupé() {
        return occupé;
    }

    public void setOccupé(Boolean occupé) {
        this.occupé = occupé;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public void setPatients(ArrayList<Patient> patients) {
        this.patients = patients;
    }
}
