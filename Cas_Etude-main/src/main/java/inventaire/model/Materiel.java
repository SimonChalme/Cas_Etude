package inventaire.model;

public class Materiel {

    private int id;
    private String nom;
    private int quantite;
    private String utilise;
    private String type;

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

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getUtilise() {
        return utilise;
    }

    public void setUtilise(String utilise) {
        this.utilise = utilise;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDci() {
        return dci;
    }

    public void setDci(String dci) {
        this.dci = dci;
    }

    public String getFormDosage() {
        return formDosage;
    }

    public void setFormDosage(String formDosage) {
        this.formDosage = formDosage;
    }

    public String getClasseTherapeutique() {
        return classeTherapeutique;
    }

    public void setClasseTherapeutique(String classeTherapeutique) {
        this.classeTherapeutique = classeTherapeutique;
    }

    public int getLot() {
        return lot;
    }

    public void setLot(int lot) {
        this.lot = lot;
    }

    private String dci;
    private String formDosage;
    private String classeTherapeutique;
    private int lot;

    public Materiel(int id, String nom, int quantite, String utilise, String type, String dci, String formDosage, String classeTherapeutique, int lot) {
        this.id = id;
        this.nom = nom;
        this.quantite = quantite;
        this.utilise = utilise;
        this.type = type;
        this.dci = dci;
        this.formDosage = formDosage;
        this.classeTherapeutique = classeTherapeutique;
        this.lot = lot;
    }







}
