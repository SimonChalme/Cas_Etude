package utilisateur.model;

import utilisateur.DAO.DaoManager;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static UserManager instance = null;
    private List<User> mesUsers;
    private ArrayList<Patient> listeVictimes;

    private ArrayList<Mission> listeMissions;

    public ArrayList<Secouristes> getSecourLibre() {
        return secourLibre;
    }

    public void setSecourLibre(ArrayList<Secouristes> secourLibre) {
        this.secourLibre = secourLibre;
    }

    private ArrayList<Secouristes> secourLibre;

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


    private DaoManager daoManager = new DaoManager();

    public ArrayList<Patient> getListeVictimes() {
        return listeVictimes;
    }

    public void setListeVictimes(ArrayList<Patient> listeVictimes) {
        this.listeVictimes = listeVictimes;
    }


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
