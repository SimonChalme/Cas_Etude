package utilisateur.DAO;

import utilisateur.model.Mission;
import utilisateur.model.Patient;
import utilisateur.model.Secouristes;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Logger;

public class DaoManager {
    private static final Logger log = Logger.getLogger(DaoManager.class.getName());
    private Connection connection;

    public DaoManager() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/marc/IdeaProjects/Cas_Etude-main/db/db.sqlite");
            System.out.println("Opened database successfully");
            createDatabase();
            createPatientbase();
            createMissionbase();
        } catch (Exception e) {
            log.severe( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    private void createDatabase() {
        try {
            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS user (idUser INTEGER(10) NOT NULL,nom VARCHAR(30) NOT NULL,prenom VARCHAR(30) NOT NULL,motdepasse VARCHAR(20) NOT NULL,role VARCHAR(20) NOT NULL,occupe VARCHAR(10) NOT NULL,localisation  INTEGER(30),idintervention INTERGER(10),CONSTRAINT pkUser PRIMARY KEY (idUser))";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            log.severe(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private void createPatientbase(){
        try {
            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS patient (idPatient INTEGER(10) NOT NULL,nom VARCHAR(30) NOT NULL,prenom VARCHAR(30) NOT NULL,etatDeGravite VARCHAR(20) NOT NULL, idMed INTEGER(10), CONSTRAINT pkUser PRIMARY KEY (idPatient))";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            log.severe(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private void createMissionbase(){
        try {
            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE Mission (idMission INTEGER(10) NOT NULL,nomMission VARCHAR(30) NOT NULL,localisation VARCHAR(30) NOT NULL,nbVictimes INTEGER(10) NOT NULL,nbSecoursite INTEGER(10) NOT NULL,currNbSecoursite INTEGER(10) NOT NULL, CONSTRAINT pkUser PRIMARY KEY (idMission))";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            log.severe(e.getClass().getName() + ": " + e.getMessage());
        }
    }


    public String getUtilisateurByNomAndPassword(String nom, String password) {
        String role = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE nom = ? AND motdepasse = ?");
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                role = resultSet.getString("role");
                System.out.println("utilisateur trouvé");
            }
            else {
                System.out.println("pas d'utilisateur trouvé"); // ajouter un message pour indiquer qu'aucun utilisateur n'a été trouvé
            }
            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            log.severe( e.getClass().getName() + ": " + e.getMessage() );
        }
        return role;
    }

    public int getIdByNomAndPassword(String nom, String password) {
        int id = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE nom = ? AND motdepasse = ?");
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt("idUser");
                System.out.println("utilisateur trouvé");
            }
            else {
                System.out.println("pas d'utilisateur trouvé"); // ajouter un message pour indiquer qu'aucun utilisateur n'a été trouvé
            }
            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            log.severe( e.getClass().getName() + ": " + e.getMessage() );
        }
        return id;
    }

    public void addUtilisateur(int i, String nom, String prenom, String mdp, String role) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user (idUser, nom, prenom, motdepasse, role, occupe, localisation, idintervention) VALUES (?,?,?,?,?,?,?,?)");
            preparedStatement.setInt(1, i);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, prenom);
            preparedStatement.setString(4, mdp);
            preparedStatement.setString(5, role);
            preparedStatement.setString(6, "non");
            preparedStatement.setInt(7, 0);
            preparedStatement.setInt(8, 0);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            log.severe( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public void supUtilisateur(int i) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM user WHERE idUser = ?");
            preparedStatement.setInt(1, i);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            log.severe( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public void updateUtilisateur(int i, String nom, String prenom, String mdp, String role) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE user SET nom = ?, prenom = ?, motdepasse = ?, role = ? WHERE idUser = ?");
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, mdp);
            preparedStatement.setString(4, role);
            preparedStatement.setInt(5, i);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            log.severe( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public void addPatient(int i, String nom, String prenom, String etat) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO patient (idPatient, nom, prenom, etatDeGravite, idMed) VALUES (?,?,?,?,?)");
            preparedStatement.setInt(1, i);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, prenom);
            preparedStatement.setString(4, etat);
            preparedStatement.setInt(5, 0);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            log.severe( e.getClass().getName() + ": " + e.getMessage() );
        }
    }


    public void majOccupe(int currentId, String etat) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE user SET occupe = ? WHERE idUser = ?");
            preparedStatement.setString(1, etat);
            preparedStatement.setInt(2, currentId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            log.severe( e.getClass().getName() + ": " + e.getMessage() );
        }
    }


    public String getRoleById(int currentId) {
        String role = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE idUser = ?");
            preparedStatement.setInt(1, currentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                role = resultSet.getString("role");
                System.out.println("utilisateur trouvé");
            }
            else {
                System.out.println("pas d'utilisateur trouvé"); // ajouter un message pour indiquer qu'aucun utilisateur n'a été trouvé
            }
            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            log.severe( e.getClass().getName() + ": " + e.getMessage() );
        }
        return role;
    }

    public String getPrenomByNomAndPassword(String name, String password) {
        String prenom = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE nom = ? AND motdepasse = ?");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                prenom = resultSet.getString("prenom");
                System.out.println("utilisateur trouvé");
            }
            else {
                System.out.println("pas d'utilisateur trouvé"); // ajouter un message pour indiquer qu'aucun utilisateur n'a été trouvé
            }
            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            log.severe( e.getClass().getName() + ": " + e.getMessage() );
        }
        return prenom;
    }
    public String getOccupeByNom(String name) {
        String occupe = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT occupe FROM user WHERE nom = ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                occupe = resultSet.getString("occupe");
                System.out.println("utilisateur trouvé");
            } else {
                System.out.println("pas d'utilisateur trouvé"); // ajouter un message pour indiquer qu'aucun utilisateur n'a été trouvé
            }
            resultSet.close();
            preparedStatement.close();
        }
        catch (Exception e) {
            log.severe(e.getClass().getName() + ": " + e.getMessage());
        }
        return occupe;
    }

    public Boolean getOccupeByNomAndPassword(String name, String password) {
        Boolean occupe = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE nom = ? AND motdepasse = ?");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                occupe = resultSet.getString("occupe").equals("oui");
                System.out.println("utilisateur trouvé");
            }
            else {
                System.out.println("pas d'utilisateur trouvé"); // ajouter un message pour indiquer qu'aucun utilisateur n'a été trouvé
            }
            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            log.severe( e.getClass().getName() + ": " + e.getMessage() );
        }
        return occupe;
    }

    public ArrayList<Patient> getPatientsByMedId(int currentId) {
        ArrayList<Patient> patients = new ArrayList<Patient>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM patient WHERE idMed = ?");
            preparedStatement.setInt(1, currentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                patients.add(new Patient(resultSet.getInt("idPatient"), resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getInt("etatDeGravite")));
            }
            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            log.severe( e.getClass().getName() + ": " + e.getMessage() );
        }
        return patients;
    }

    public ArrayList<Patient> getVictimes() {
        ArrayList<Patient> patients = new ArrayList<Patient>();
        int MAX_PATIENTS = 10;
        int compteur = 0;
        try {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM patient WHERE idMed = 0");
            while (resultSet.next() && compteur < MAX_PATIENTS) {
                patients.add(new Patient(resultSet.getInt("idPatient"), resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getInt("etatDeGravite")));
                compteur++;
            }
            resultSet.close();
            stmt.close();
        } catch (Exception e) {
            log.severe( e.getClass().getName() + ": " + e.getMessage() );
        }
        return patients;
    }

    public void choixMedecin(int i, int currentId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE patient SET idMed = ? WHERE idPatient = ?");
            preparedStatement.setInt(1, currentId);
            preparedStatement.setInt(2, i);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            log.severe( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public Patient getPatientById(int i) {
        Patient patient = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM patient WHERE idPatient = ?");
            preparedStatement.setInt(1, i);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                patient = new Patient(resultSet.getInt("idPatient"), resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getInt("etatDeGravite"));
                System.out.println("patient trouvé");
            }
            else {
                System.out.println("pas de patient trouvé"); // ajouter un message pour indiquer qu'aucun utilisateur n'a été trouvé
            }
            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            log.severe( e.getClass().getName() + ": " + e.getMessage() );
        }
        return patient;
    }

    public void finCharge(int i) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM patient WHERE idPatient = ?");
            preparedStatement.setInt(1, i);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            log.severe( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public void addMission(String id, String nom, String localisation, int i, int i1) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Mission (idMission, nomMission, localisation, nbVictimes, nbSecoursite, currNbSecoursite) VALUES (?,?,?,?,?,?)");
            preparedStatement.setInt(1, Integer.parseInt(id));
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, localisation);
            preparedStatement.setInt(4, i);
            preparedStatement.setInt(5, i1);
            preparedStatement.setInt(6, 0);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            log.severe( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public ArrayList<Mission> getMissions() {
        ArrayList<Mission> missions = new ArrayList<Mission>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM Mission WHERE nbVictimes != 0");
            while (resultSet.next()) {
                missions.add(new Mission(resultSet.getInt("idMission"), resultSet.getString("nomMission"), resultSet.getString("localisation"), resultSet.getInt("nbVictimes"), resultSet.getInt("nbSecoursite"), resultSet.getInt("currNbSecoursite")));
            }
            resultSet.close();
            stmt.close();
        } catch (Exception e) {
            log.severe( e.getClass().getName() + ": " + e.getMessage() );
        }
        return missions;
    }

    public ArrayList<Secouristes> getSecouristesLibres() {
        ArrayList<Secouristes> secouristes = new ArrayList<Secouristes>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM user WHERE role = 'secouriste' AND occupe = 'non'");
            while (resultSet.next()) {
                secouristes.add(new Secouristes(resultSet.getInt("idUser"), resultSet.getString("nom"), resultSet.getString("prenom"), false, resultSet.getString("localisation"), resultSet.getInt("idintervention")));
            }
            resultSet.close();
            stmt.close();
        } catch (Exception e) {
            log.severe( e.getClass().getName() + ": " + e.getMessage() );
        }
        return secouristes;
    }



    public void updateSecouristeMission(String nom) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Mission SET currNbSecoursite = currNbSecoursite + 1 WHERE idMission= ?");
            preparedStatement.setString(1, nom);
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (Exception e) {
            log.severe( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public void affecterSecouriste(String id, String idMission) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE user SET occupe = 'oui', idintervention=? WHERE idUser = ?");
            preparedStatement.setInt(1, Integer.parseInt(idMission));
            preparedStatement.setInt(2, Integer.parseInt(id));
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (Exception e) {
            log.severe( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public String getIdInterventionByNomAndPassword(String name, String password) {
        String idIntervention = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE nom = ? AND motdepasse = ?");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                idIntervention = resultSet.getString("idintervention");
                System.out.println("utilisateur trouvé");
            }
            else {
                System.out.println("pas d'utilisateur trouvé"); // ajouter un message pour indiquer qu'aucun utilisateur n'a été trouvé
            }
            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            log.severe( e.getClass().getName() + ": " + e.getMessage() );
        }
        return idIntervention;
    }

    public void supVictimeMission(String idIntervention) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Mission SET nbVictimes = nbVictimes - 1 WHERE idMission = ?");
            preparedStatement.setInt(1, Integer.parseInt(idIntervention));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            log.severe( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public int getNbVictimesMission(String idIntervention) {
        int nbVictimes = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Mission WHERE idMission = ?");
            preparedStatement.setInt(1, Integer.parseInt(idIntervention));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                nbVictimes = resultSet.getInt("nbVictimes");
                System.out.println("mission trouvée");
            }
            else {
                System.out.println("pas de mission trouvée"); // ajouter un message pour indiquer qu'aucun utilisateur n'a été trouvé
            }
            resultSet.close();
            preparedStatement.close();
        } catch (Exception e) {
            log.severe( e.getClass().getName() + ": " + e.getMessage() );
        }
        return nbVictimes;
    }

    public void liberSecouristes(String idIntervention) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE user SET occupe = 'non', idintervention = 0 WHERE idintervention = ?");
            preparedStatement.setInt(1, Integer.parseInt(idIntervention));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            log.severe( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
}
