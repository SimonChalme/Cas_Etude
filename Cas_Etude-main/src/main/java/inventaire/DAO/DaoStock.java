package inventaire.DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;
import inventaire.model.Commande;
import inventaire.model.Materiel;

public class DaoStock {
    private static final Logger log = Logger.getLogger(DaoStock.class.getName());
    private Connection connection;

    public DaoStock() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/marc/IdeaProjects/Cas_Etude-main/db/db.sqlite");
            System.out.println("Opened database successfully");
            createMaterielbase();
            createCommandebase();
        } catch (Exception e) {
            log.severe(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private void createMaterielbase() {
        try {
            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS materiel (idMateriel INTEGER PRIMARY KEY AUTOINCREMENT,nom VARCHAR(30) NOT NULL,quantite INTEGER(10) NOT NULL,type VARCHAR(20) NOT NULL,utilise VARCHAR(10), DateLimiteConsommation VARCHAR(10), lot INTEGER(10), classeTherapeutique VARCHAR(30),formDosage VARCHAR(30))";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            log.severe(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private void createCommandebase() {
        try {
            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Commande (idCommande INTEGER PRIMARY KEY AUTOINCREMENT,priorité INTEGER(30) NOT NULL,type VARCHAR(30) NOT NULL,dateCommande VARCHAR(10) NOT NULL,matCommande VARCHAR(30) NOT NULL,quantite INTEGER(10) NOT NULL)";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            log.severe(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public ArrayList<Commande> getCommandes() {
        ArrayList<Commande> commandes = new ArrayList<Commande>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Commande");
            while (resultSet.next()) {
                Commande commande = new Commande(resultSet.getInt("idCommande"), resultSet.getInt("priorité"), resultSet.getString("type"), resultSet.getString("dateCommande"), resultSet.getString("matCommande"), resultSet.getInt("quantite"));
                commandes.add(commande);
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            log.severe(e.getClass().getName() + ": " + e.getMessage());
        }
        return commandes;
    }

    public ArrayList<Materiel> getMaterielsMedicals() {

        ArrayList<Materiel> materiels = new ArrayList<Materiel>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM materiel WHERE type = 'medical' ORDER BY LOWER(nom) ASC");
            while (resultSet.next()) {
                Materiel materiel = new Materiel(resultSet.getInt("idMateriel"), resultSet.getString("nom"), resultSet.getInt("quantite"), resultSet.getString("utilise"), resultSet.getString("type"), resultSet.getString("DateLimiteConsommation"), resultSet.getString("formDosage"), resultSet.getString("classeTherapeutique"),resultSet.getInt("lot"));
                materiels.add(materiel);
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            log.severe(e.getClass().getName() + ": " + e.getMessage());
        }
        return materiels;
    }

    public ArrayList<Materiel> getMaterielsLogistiques() {
        ArrayList<Materiel> materiels = new ArrayList<Materiel>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM materiel WHERE type = 'logistique' ORDER BY LOWER(nom) ASC ");
            while (resultSet.next()) {
                Materiel materiel = new Materiel(resultSet.getInt("idMateriel"), resultSet.getString("nom"), resultSet.getInt("quantite"), resultSet.getString("utilise"), resultSet.getString("type"), resultSet.getString("DateLimiteConsommation"), resultSet.getString("formDosage"), resultSet.getString("classeTherapeutique"),resultSet.getInt("lot"));
                materiels.add(materiel);
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            log.severe(e.getClass().getName() + ": " + e.getMessage());
        }
        return materiels;
    }

    //à revoir
    public void ajoutMateriel(String nom, int quantite, String utilise, String type, String dci, String formDosage, String classeTherapeutique, int lot) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO materiel (nom, quantite, utilise, type, DateLimiteConsommation, formDosage, classeTherapeutique, lot) VALUES (?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, nom);
            preparedStatement.setInt(2, quantite);
            preparedStatement.setString(3, utilise);
            preparedStatement.setString(4, type);
            preparedStatement.setString(5, dci);
            preparedStatement.setString(6, formDosage);
            preparedStatement.setString(7, classeTherapeutique);
            preparedStatement.setInt(8, lot);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            log.severe(e.getClass().getName() + ": " + e.getMessage());
        }
    }


    public void updateStock(String nom, int quantite, int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE materiel SET quantite = ? WHERE nom = ? AND idMateriel = ?");
            preparedStatement.setInt(1, quantite);
            preparedStatement.setString(2, nom);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            log.severe(e.getClass().getName() + ": " + e.getMessage());
        }

    }

    public void createCommandeMateriel(String nom, String type){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Commande (priorité, type, dateCommande, matCommande, quantite) VALUES (?,?,?,?,?)");
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, type);
            preparedStatement.setString(3, "0");
            preparedStatement.setString(4, nom);
            preparedStatement.setInt(5, 1);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            log.severe(e.getClass().getName() + ": " + e.getMessage());
        }

    }

    public void supprimeMateriel(String nom, int id){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM materiel WHERE nom = ? AND idMateriel = ?");
            preparedStatement.setString(1, nom);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            log.severe(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public void updateUtilisation(String nom, String utilisation, int id){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE materiel SET utilise = ? WHERE nom = ? AND idMateriel = ?");
            preparedStatement.setString(1, utilisation);
            preparedStatement.setString(2, nom);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            log.severe(e.getClass().getName() + ": " + e.getMessage());
        }

    }

    public void supprimeCommande(int id, String nom){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Commande WHERE idCommande = ? AND matCommande = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, nom);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            log.severe(e.getClass().getName() + ": " + e.getMessage());
        }

    }

    public String recupMdp(String nom){
        String mdp = "";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT mdp FROM utilisateur WHERE nom = '"+nom+"'");
            while (resultSet.next()) {
                mdp = resultSet.getString("mdp");
            }
            resultSet.close();
            statement.close();
        } catch (Exception e) {
            log.severe(e.getClass().getName() + ": " + e.getMessage());
        }
        return mdp;
    }


}
