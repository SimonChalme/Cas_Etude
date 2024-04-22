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

@WebServlet(name = "Control", value = "/Control")
public class Control extends HttpServlet {
    private int currentId = 0;
    private DaoManager daoManager = new DaoManager();

    private String idIntervention = "";

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {


        String vueFinale = doAction(request);

        request.getRequestDispatcher(vueFinale).forward(request, response);

    }

    private void syncApplication(){
        UserManager.getInstance().setListeVictimes(daoManager.getVictimes());
        UserManager.getInstance().setListeMissions(daoManager.getMissions());
    }

    private String doAction(HttpServletRequest request) throws IOException, ServletException {

        String vueFinale = "connexion.jsp";

        String action = request.getParameter("action");

        if (action.equals("Connexion")) {

            String name = request.getParameter("nom");
            String password = request.getParameter("mdp");
            HttpSession session = request.getSession();
            session.setAttribute("name", name);


            if (daoManager.getUtilisateurByNomAndPassword(name, password) != null) {
                String role = daoManager.getUtilisateurByNomAndPassword(name, password);
                currentId = daoManager.getIdByNomAndPassword(name, password);
                String prenom = daoManager.getPrenomByNomAndPassword(name, password);


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
                vueFinale = role+".jsp";
            } else {
                vueFinale = "connexion.jsp";
            }
        }


        if(action.equals("addUser")){
            String id = request.getParameter("id");
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String mdp = request.getParameter("mdp");
            String role = request.getParameter("role");
            UserManager.getInstance().addUser(Integer.parseInt(id), nom, prenom);
            daoManager.addUtilisateur(Integer.parseInt(id), nom, prenom, mdp, role);
            vueFinale = "admin.jsp";
        }

        if(action.equals("supUser")){
            String id = request.getParameter("id");
            UserManager.getInstance().deleteUser(Integer.parseInt(id));
            daoManager.supUtilisateur(Integer.parseInt(id));
            vueFinale = "admin.jsp";
        }

        if(action.equals("updateUser")){
            String id = request.getParameter("id");
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String mdp = request.getParameter("mdp");
            String role = request.getParameter("role");
            UserManager.getInstance().updateUser(Integer.parseInt(id), nom, prenom);
            daoManager.updateUtilisateur(Integer.parseInt(id), nom, prenom, mdp, role);
            vueFinale = "admin.jsp";

        }

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
            vueFinale = "secouriste.jsp";
        }

        if(action.equals("Deconnexion")){
            vueFinale = "connexion.jsp";
        }

        if(action.equals("majOccupe")){
            String etat = request.getParameter("etat");
            daoManager.majOccupe(currentId, etat);
            String role= daoManager.getRoleById(currentId);
            vueFinale = role+".jsp";
        }

        if(action.equals("PrendreEnCharge")){
            String id = request.getParameter("idPatient");
            daoManager.choixMedecin(Integer.parseInt(id), currentId);
            UserManager.getInstance().setListeVictimes(daoManager.getVictimes());
            UserManager.getInstance().getCurrentMedecin().getPatients().add(daoManager.getPatientById(Integer.parseInt(id)));
            vueFinale = "medical.jsp";
        }

        if(action.equals("Fin Prise en charge")){
            String id = request.getParameter("idPatient");
            daoManager.finCharge(Integer.parseInt(id));
            UserManager.getInstance().getCurrentMedecin().getPatients().remove(daoManager.getPatientById(Integer.parseInt(id)));
            syncApplication();
            vueFinale = "medical.jsp";
        }

        if(action.equals("createMission")){
            String id = request.getParameter("idMission");
            String nom = request.getParameter("nom");
            String localisation = request.getParameter("loc");
            String nbVictimes = request.getParameter("nbVict");
            String nbSecoursite = request.getParameter("nbSecour");
            daoManager.addMission(id,nom, localisation, Integer.parseInt(nbVictimes), Integer.parseInt(nbSecoursite));
            vueFinale = "admin.jsp";
        }

        if(action.equals("affecter")){
            String id = request.getParameter("libre");
            String idMission = request.getParameter("id");
            daoManager.updateSecouristeMission(idMission);
            daoManager.affecterSecouriste(id, idMission);
            vueFinale = "admin.jsp";
        }

        return vueFinale;
    }

    public void destroy() {
    }

}