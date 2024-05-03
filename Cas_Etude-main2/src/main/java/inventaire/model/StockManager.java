package inventaire.model;

import inventaire.DAO.DaoStock;


import java.util.ArrayList;

// création de la classe StockManager (équivalent de la classe Application)
public class StockManager {
    // déclaration des attibuts de la classe StockManager, instance est initialisé à null pour respecter le singleton
    // on initialise 3 listes qui seront mises à jour avec les valeurs de la base de données voulues via la classe DAO après une action traitée dans la classe contrôle.
    // Ces listes de l'instance unique vont permettre de gérer l'affichage d'informations de la base de données dans la vue.
    private static StockManager instance = null;
    private DaoStock daoStock = new DaoStock();
    public static StockManager getInstance() {
        if (instance == null) {
            instance = new StockManager();
        }
        return instance;
    }

    public DaoStock getDaoStock() {
        return daoStock;
    }

    public void setDaoStock(DaoStock daoStock) {
        this.daoStock = daoStock;
    }

    private ArrayList<Commande> lesCommandes;
    private ArrayList<Materiel> lesMaterielsMedicals;

    private ArrayList<Materiel> lesMaterielsLogistiques;

    public ArrayList<Materiel> getLesMaterielsMedicals() {
        return lesMaterielsMedicals;
    }

    public void setLesMaterielsMedicals(ArrayList<Materiel> lesMaterielsMedicals) {
        this.lesMaterielsMedicals = lesMaterielsMedicals;
    }

    public ArrayList<Materiel> getLesMaterielsLogistiques() {
        return lesMaterielsLogistiques;
    }

    public void setLesMaterielsLogistiques(ArrayList<Materiel> lesMaterielsLogistiques) {
        this.lesMaterielsLogistiques = lesMaterielsLogistiques;
    }

    private StockManager(){
        lesCommandes = daoStock.getCommandes();
        lesMaterielsMedicals = daoStock.getMaterielsMedicals();
        lesMaterielsLogistiques = daoStock.getMaterielsLogistiques();
    }



    public ArrayList<Commande> getLesCommandes() {
        return lesCommandes;
    }

    public void setLesCommandes(ArrayList<Commande> lesCommandes) {
        this.lesCommandes = lesCommandes;
    }

}
