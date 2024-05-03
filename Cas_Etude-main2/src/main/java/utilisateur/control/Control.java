package utilisateur.control;
import inventaire.model.StockManager;
import utilisateur.DAO.DaoManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import utilisateur.model.Patient;
import utilisateur.model.PersonelDeSoin;
import utilisateur.model.User;
import utilisateur.model.UserManager;

//Création d'un classe control pour le dialogue entre la vue et le modèle
@WebServlet(name = "Control", value = "/Control")
public class Control extends HttpServlet {

    // initialisation des variables de session et du gestionnaire de base de donnée
    private int currentId = 0;
    private DaoManager daoManager = new DaoManager();
    private String idIntervention = "";

    // Méthode de traitement des requêtes GET
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {


        String vueFinale = doAction(request);

        request.getRequestDispatcher(vueFinale).forward(request, response);

    }

    // synchronisation de l'application
    private void syncApplication(){
        UserManager.getInstance().setListeVictimes(daoManager.getVictimes());
        UserManager.getInstance().setListeMissions(daoManager.getMissions());
        UserManager.getInstance().setSecourLibre(daoManager.getSecouristesLibres());
    }

    // Méthode de traitement des requêtes DO
    private String doAction(HttpServletRequest request) throws IOException, ServletException {

        String vueFinale = "connexion.jsp";

        String action = request.getParameter("action");

        // Gestion d la connexion de l'utilisateur
        if (action.equals("Connexion")) {

            // création d'un sessio avec les informations de l'utilisateur récupérées par le formulaire
            String name = request.getParameter("nom");
            String password = request.getParameter("mdp");
            HttpSession session = request.getSession();
            session.setAttribute("name", name);

            // intialisation du rôle de l'utilisateur et de son Id pour le traitement de ses information par le modèle
            if (daoManager.getUtilisateurByNomAndPassword(name, password) != null) {
                String role = daoManager.getUtilisateurByNomAndPassword(name, password);
                currentId = daoManager.getIdByNomAndPassword(name, password);
                String prenom = daoManager.getPrenomByNomAndPassword(name, password);

                // intialisation des information supplémentaire dans le cas de figure où l'utilisateur est un secouriste ou un médecin
                if("medical".equals(role)){
                    System.out.println("je suis un medecin");
                    Boolean occupe = daoManager.getOccupeByNomAndPassword(name, password);
                    ArrayList<Patient> ListePatients = daoManager.getPatientsByMedId(currentId);
                    PersonelDeSoin med = new PersonelDeSoin(currentId, name, prenom, occupe, ListePatients);
                    UserManager.getInstance().setCurrentMedecin(med);
                }

                if("secouriste".equals(role)){
                    System.out.println("je suis un secouriste");
                    Boolean occupe = daoManager.getOccupeByNomAndPassword(name, password);
                    idIntervention = daoManager.getIdInterventionByNomAndPassword(name, password);

                }
                System.out.println(currentId);
                // redirection vers la page correspondante au rôle de l'utilisateur
                vueFinale = role+".jsp";
            } else {
                // redirection vers la page de connexion en cas d'erreur
                vueFinale = "connexion.jsp";
            }
        }


        // traitement de l'action ajout d'utilisateur, on récupère les informations du formulaire et on les ajoute à la base de donné ainsi qu'au modèle
        // on sychronise ensuite l'application pour gérér l'affihcage dynamique sur la page
        if(action.equals("addUser")){
            String id = request.getParameter("id");
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String mdp = request.getParameter("mdp");
            String role = request.getParameter("role");
            UserManager.getInstance().addUser(Integer.parseInt(id), nom, prenom);
            daoManager.addUtilisateur(Integer.parseInt(id), nom, prenom, mdp, role);
            syncApplication();
            vueFinale = "admin.jsp";
        }
         // traitement de l'action suppression d'utilisateur, on récupère l'id de l'utilisateur à supprimer et on le supprime de la base de donnée ainsi que du modèle
        // on sychronise ensuite l'application pour gérér l'affichage dynamique sur la page
        if(action.equals("supUser")){
            String id = request.getParameter("id");
            UserManager.getInstance().deleteUser(Integer.parseInt(id));
            daoManager.supUtilisateur(Integer.parseInt(id));
            syncApplication();
            vueFinale = "admin.jsp";
        }

        // traitement de l'action modification d'utilisateur, on récupère les informations du formulaire et on les modifie dans la base de donnée ainsi que dans le modèle
        // on sychronise ensuite l'application pour gérér l'affihcage dynamique sur la page
        if(action.equals("updateUser")){
            String id = request.getParameter("id");
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String mdp = request.getParameter("mdp");
            String role = request.getParameter("role");
            UserManager.getInstance().updateUser(Integer.parseInt(id), nom, prenom);
            daoManager.updateUtilisateur(Integer.parseInt(id), nom, prenom, mdp, role);
            syncApplication();
            vueFinale = "admin.jsp";

        }

        // traitement de l'action ajout de victime, on récupère les inforùtions du formulaire et on ajoute un nouveau patient sur la mission en cours récupérée au moment de
        // l'initialisation des informations du secouriste (avec idIntervention). On met ensuite à jour le nombre de victimes restantes à trouver.
        // Si le nombre de victimes à trouver est atteint, on libère les secouristes de la mission
        if(action.equals("addPatient")){
            String id = request.getParameter("id");
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String etat = request.getParameter("etat");
            daoManager.addPatient(Integer.parseInt(id), nom, prenom, etat);
            daoManager.supVictimeMission(idIntervention);
            UserManager.getInstance().setListeVictimes(daoManager.getVictimes());
            if(daoManager.getNbVictimesMission(idIntervention) == 0){
                daoManager.liberSecouristes(idIntervention);
            }
            syncApplication();
            vueFinale = "secouriste.jsp";
        }

        // traitement de l'action deccnnexion de l'uitilisateur
        if(action.equals("Deconnexion")){
            vueFinale = "connexion.jsp";
        }

        // traitement de l'action mise à jour de l'état d'occupation de l'utilisateur connecté
        if(action.equals("majOccupe")){
            String etat = request.getParameter("etat");
            daoManager.majOccupe(currentId, etat);
            String role= daoManager.getRoleById(currentId);
            vueFinale = role+".jsp";
        }

        // traitement de l'action de prise en charge d'un patient par un médecin
        if(action.equals("PrendreEnCharge")){
            String id = request.getParameter("idPatient");
            daoManager.choixMedecin(Integer.parseInt(id), currentId);
            UserManager.getInstance().setListeVictimes(daoManager.getVictimes());
            UserManager.getInstance().getCurrentMedecin().getPatients().add(daoManager.getPatientById(Integer.parseInt(id)));
            vueFinale = "medical.jsp";
        }

        // supression d'un patient de la liste des patients pris en charge par le médecin, on sychronise ensuite l'application pour gérér l'affihcage dynamique sur la page
        if(action.equals("Fin Prise en charge")){
            String id = request.getParameter("idPatient");
            daoManager.finCharge(Integer.parseInt(id));
            UserManager.getInstance().getCurrentMedecin().getPatients().remove(daoManager.getPatientById(Integer.parseInt(id)));
            syncApplication();
            vueFinale = "medical.jsp";
        }

        // traitement de l'action de création de mission, on récupère les informations du formulaire et on crée une nouvelle mission dans la base de donnée, on sychronise ensuite l'application pour gérér l'affihcage dynamique sur la page
        if(action.equals("createMission")){
            String id = request.getParameter("idMission");
            String nom = request.getParameter("nom");
            String localisation = request.getParameter("loc");
            String nbVictimes = request.getParameter("nbVict");
            String nbSecoursite = request.getParameter("nbSecour");
            daoManager.addMission(id,nom, localisation, Integer.parseInt(nbVictimes), Integer.parseInt(nbSecoursite));
            syncApplication();
            vueFinale = "admin.jsp";
        }

        // traitement de l'affectation d'un secouriste libre sur une mission
        // on sychronise ensuite l'application pour gérér l'affihcage dynamique sur la page
        if(action.equals("affecter")){
            String id = request.getParameter("libre");
            String idMission = request.getParameter("id");
            daoManager.updateSecouristeMission(idMission);
            daoManager.affecterSecouriste(id, idMission);
            syncApplication();
            vueFinale = "admin.jsp";
        }

        return vueFinale;
    }

    public void destroy() {
    }

}