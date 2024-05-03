package utilisateur.model;

// création de l'objet personel de logistique avec ses attributs, getter et setter
public class PersonelLogistique extends User{
    private Boolean occupé;

    public PersonelLogistique(int id, String nom, String prenom, Boolean occupé) {
        super(id, nom, prenom);
        this.occupé = occupé;
    }

    public Boolean getOccupé() {
        return occupé;
    }

    public void setOccupé(Boolean occupé) {
        this.occupé = occupé;
    }
}
