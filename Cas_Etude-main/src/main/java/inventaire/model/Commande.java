package inventaire.model;

public class Commande {
    private int id;
    private int priorite;
    private String type;
    private String dateCommande;
    private String matCommande;
    private int quantite;



    public Commande(int id, int priorite, String type, String dateCommande, String matCommande, int quantite) {
        this.id = id;
        this.priorite = priorite;
        this.type = type;
        this.dateCommande = dateCommande;
        this.matCommande = matCommande;
        this.quantite = quantite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriorite() {
        return priorite;
    }

    public void setPriorite(int priorite) {
        this.priorite = priorite;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(String dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getMatCommande() {
        return matCommande;
    }

    public void setMatCommande(String matCommande) {
        this.matCommande = matCommande;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

}
