package utilisateur.model;

import utilisateur.DAO.DaoManager;

import java.util.ArrayList;
import java.util.List;

// création de la classe UserManager (équivalent de la classe Application)
public class UserManager {

    // déclaration des attibuts de la classe UserManager, instance est initialisé à null pour respecter le singleton
    // on initialise 4 listes qui seront mise à jour avec les valeurs de la base de données voulues via la classe DAO après un action traité dans la classe contrôle.
    // Ces listes de l'instance unique vont permettre de gérer l'affichage d'informations de la base de donnée dans la vue.
    private static UserManager instance = null;
    private List<User> mesUsers;
    private ArrayList<Patient> listeVictimes;
    private ArrayList<Secouristes> secourLibre;
    private ArrayList<Mission> listeMissions;

    public ArrayList<Secouristes> getSecourLibre() {
        return secourLibre;
    }

    public void setSecourLibre(ArrayList<Secouristes> secourLibre) {
        this.secourLibre = secourLibre;
    }

    public ArrayList<Mission> getListeMissions() {
        return listeMissions;
    }

    public void setListeMissions(ArrayList<Mission> listeMissions) {
        this.listeMissions = listeMissions;
    }


    private PersonelDeSoin currentMedecin = new PersonelDeSoin(0, "", "", false, new ArrayList<Patient>());

    public PersonelDeSoin getCurrentMedecin() {
        return currentMedecin;
    }

    public void setCurrentMedecin(PersonelDeSoin currentMedecin) {
        this.currentMedecin = currentMedecin;
    }


    public ArrayList<Patient> getListeVictimes() {
        return listeVictimes;
    }

    public void setListeVictimes(ArrayList<Patient> listeVictimes) {
        this.listeVictimes = listeVictimes;
    }

    // intialisation d'un DAOManager pour pouvoir accéder aux méthodes de la classe DAO
    private DaoManager daoManager = new DaoManager();

    // intialisation de l'instance avec le patern singleton
    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }


    private UserManager(){
        mesUsers = new ArrayList<User>();
        listeVictimes = daoManager.getVictimes();
        listeMissions = daoManager.getMissions();
        secourLibre = daoManager.getSecouristesLibres();
    }


    // gestion des utilisateurs
    public void addUser(int id, String nom, String prenom) {
        User user = new User(id, nom, prenom);
        mesUsers.add(user);
    }


    public void deleteUser(int i) {
        for (User user : mesUsers) {
            if (user.getId() == i) {
                mesUsers.remove(user);
                break;
            }
        }
    }

    public void updateUser(int i, String nom, String prenom) {
        for (User user : mesUsers) {
            if (user.getId() == i) {
                user.setNom(nom);
                user.setPrenom(prenom);
                break;
            }
        }
    }

}
