package utilisateur.model;

public class Patient {
    private int id;
    private String nom;
    private String prenom;
    private int etatGravite;

    public Patient(int id, String nom, String prenom, int etatGravite) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.etatGravite = etatGravite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getEtatGravite() {
        return etatGravite;
    }

    public void setEtatGravite(int etatGravite) {
        this.etatGravite = etatGravite;
    }
}
